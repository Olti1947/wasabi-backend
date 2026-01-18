package com.sushi.wasabi.services;

import com.sushi.wasabi.dto.CartItemRequest;
import com.sushi.wasabi.dto.CheckoutPreviewDto;
import com.sushi.wasabi.dto.CheckoutRequest;
import com.sushi.wasabi.dto.DiscountDto;
import com.sushi.wasabi.entity.*;
import com.sushi.wasabi.enums.DiscountType;
import com.sushi.wasabi.enums.OrderStatus;
import com.sushi.wasabi.enums.UserDiscountStatus;
import com.sushi.wasabi.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckoutService {

    private final FoodItemRepository foodItemRepository;
    private final UserDiscountRepository userDiscountRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderDiscountRepository orderDiscountRepository;
    private final DiscountProductRepository discountProductRepository;
    private final DiscountService discountService;

    private Map<Integer, FoodItem> loadFoodItems(CheckoutRequest request) {

        Set<Integer> foodItemIds = request.getItems()
                .stream()
                .map(CartItemRequest::getFoodItemId)
                .collect(Collectors.toSet());

        List<FoodItem> foodItems = foodItemRepository.findAllById(foodItemIds);

        if (foodItems.size() != foodItemIds.size()) {
            throw new RuntimeException("One or more food items not found");
        }

        return foodItems.stream()
                .collect(Collectors.toMap(FoodItem::getId, Function.identity()));
    }


    private DiscountResult applyDiscount(
            User user,
            Long discountId,
            BigDecimal subtotal,
            List<OrderItem> orderItems
    ){
        UserDiscount userDiscount = userDiscountRepository.findByUser_IdAndDiscount_IdAndStatus(
                user.getId(),
                discountId,
                UserDiscountStatus.ACTIVE
        )
                .orElseThrow(()-> new RuntimeException("Discount not available"));

        Discount discount = userDiscount.getDiscount();
        LocalDateTime now = LocalDateTime.now();

        if(!discount.isActive()
            || now.isBefore(discount.getStartsAt())
            || now.isAfter(discount.getEndsAt())
            || now.isAfter(userDiscount.getExpiresAt())
        ){
        throw new RuntimeException("Discount expired");
        }

        if (subtotal.compareTo(discount.getMinOrderValue()) < 0) {
            throw new RuntimeException("Order value too low for discount");
        }

        // Applicable products
        Set<Integer> productIdsInCart = orderItems.stream()
                .map(i -> i.getFoodItem().getId())
                .collect(Collectors.toSet());

        boolean applies = discountProductRepository
                .existsByIdDiscountIdAndIdProductIdIn(discount.getId(),
                        productIdsInCart);

        if(!applies){
            throw new RuntimeException("Discount does not apply to cart items");
        }

        BigDecimal discountBase = BigDecimal.ZERO;

        Set<Integer> discountedProductIds = discountProductRepository
                .findByIdDiscountId(discount.getId())
                .stream()
                .map(dp -> dp.getFoodItem().getId())
                .collect(Collectors.toSet());

        for(OrderItem item : orderItems){
            if(discountedProductIds.contains(item.getFoodItem().getId())){
                discountBase = discountBase.add(item.getTotalPrice());
            }
        }

        BigDecimal discountAmount;

        if(discount.getType() == DiscountType.PERCENTAGE){
            discountAmount =
                    discountBase
                            .multiply(discount.getValue())
                            .divide(BigDecimal.valueOf(100));
        } else {
            discountAmount = discount.getValue();
        }

        discountAmount = discountAmount.min(discountBase);

        return new DiscountResult(discount, userDiscount, discountAmount);

    }

    public Order checkout(User user, CheckoutRequest request){
        if(request.getItems() == null || request.getItems().isEmpty()){
            throw new IllegalArgumentException("Cart is empty");
        }

        Map<Integer, FoodItem> foodItems = loadFoodItems(request);
        BigDecimal subtotal = BigDecimal.ZERO;

        List<OrderItem> orderItems = new ArrayList<>();

        for(CartItemRequest cartItem : request.getItems()){
            FoodItem foodItem = foodItems.get(cartItem.getFoodItemId());
            BigDecimal itemTotal = foodItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            subtotal = subtotal.add(itemTotal);

            OrderItem orderItem = new OrderItem();
            orderItem.setFoodItem(foodItem);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(foodItem.getPrice());
            orderItem.setTotalPrice(itemTotal);

            orderItems.add(orderItem);
        }

        BigDecimal discountTotal = BigDecimal.ZERO;
        Discount appliedDiscount = null;
        UserDiscount userDiscount = null;

        if(request.getDiscountId() != null){
            DiscountResult result = applyDiscount(
                    user,
                    request.getDiscountId(),
                    subtotal,
                    orderItems
            );

            discountTotal = result.discountAmount();
            appliedDiscount = result.discount();
            userDiscount = result.userDiscount();
        }

        BigDecimal total = subtotal.subtract(discountTotal);

        Order order = new Order();
        order.setUser(user);
        order.setSubtotal(subtotal);
        order.setDiscountTotal(discountTotal);
        order.setTotal(total);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        order = orderRepository.save(order);

        for(OrderItem item : orderItems){
            item.setOrder(order);
            orderItemRepository.save(item);
        }

        if(appliedDiscount != null){
            OrderDiscount od = new OrderDiscount();
            od.setOrder(order);
            od.setDiscount(appliedDiscount);
            od.setDiscountAmount(discountTotal);
            orderDiscountRepository.save(od);

            userDiscount.setStatus(UserDiscountStatus.USED);
            userDiscount.setUsedAt(LocalDateTime.now());
            userDiscountRepository.save(userDiscount);
        }

        return order;
    }

    @Transactional
    public CheckoutPreviewDto preview(User user, CheckoutRequest request) {
        if(request.getItems() == null || request.getItems().isEmpty()){
            throw new IllegalArgumentException("Cart is empty");
        }

        Map<Integer, FoodItem> foodItems = loadFoodItems(request);
        BigDecimal subtotal = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for(CartItemRequest cartItem: request.getItems()){
            FoodItem foodItem = foodItems.get(cartItem.getFoodItemId());
            BigDecimal itemTotal = foodItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            subtotal = subtotal.add(itemTotal);

            OrderItem orderItem = new OrderItem();
            orderItem.setFoodItem(foodItem);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(foodItem.getPrice());
            orderItem.setTotalPrice(itemTotal);
            orderItems.add(orderItem);
        }

        BigDecimal discountTotal = BigDecimal.ZERO;
        Discount appliedDiscount = null;

        if(request.getDiscountId() != null){
            DiscountResult result = applyDiscount(
                    user,
                    request.getDiscountId(),
                    subtotal,
                    orderItems
            );

            discountTotal = result.discountAmount();
            appliedDiscount = result.discount();
        }
        BigDecimal total = subtotal.subtract(discountTotal);

        return CheckoutPreviewDto.builder()
                .subtotal(subtotal)
                .discount(discountTotal)
                .total(total)
                .appliedDiscount(appliedDiscount != null ? appliedDiscount.getTitle(): null)
                .build();
    }

    public List<DiscountDto> applicableDiscounts(
            List<CartItemRequest> request,
            User user
    ) {

        if (request == null || request.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        List<DiscountDto> activeDiscounts =
                discountService.getUserActiveDiscounts(user.getId());

        if (activeDiscounts.isEmpty()) {
            return List.of();
        }

        Set<Long> discountIds = activeDiscounts.stream()
                .map(DiscountDto::getId)
                .collect(Collectors.toSet());

        Set<Integer> productIds = request.stream()
                .map(CartItemRequest::getFoodItemId)
                .collect(Collectors.toSet());

        Set<Long> applicableDiscountIds =
                discountProductRepository.findApplicableDiscountIds(
                        discountIds,
                        productIds
                );

        if (applicableDiscountIds.isEmpty()) {
            return List.of();
        }

        return activeDiscounts.stream()
                .filter(d -> applicableDiscountIds.contains(d.getId()))
                .toList();
    }

}
