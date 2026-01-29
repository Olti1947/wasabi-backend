package com.sushi.wasabi.services;

import com.sushi.wasabi.dto.DiscountAdminRequest;
import com.sushi.wasabi.dto.DiscountDto;
import com.sushi.wasabi.entity.*;
import com.sushi.wasabi.enums.UserDiscountStatus;
import com.sushi.wasabi.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountService {
private final DiscountRepository discountRepository;
private final UserDiscountRepository userDiscountRepository;
private final UserRepository userRepository;
private final FoodItemRepository foodItemRepository;
private final DiscountProductRepository discountProductRepository;

public List<DiscountDto> getAvailableDiscounts(Integer userId){
    LocalDateTime now = LocalDateTime.now();

    return discountRepository.findAllActive(now)
            .stream()
            .filter(d -> !userDiscountRepository.existsByUser_IdAndDiscount_Id(userId, d.getId()))
            .map(this::toDto)
            .collect(Collectors.toList());
}

public void activateDiscount(Integer userId, Long discountId){
    Discount discount = discountRepository.findById(discountId)
            .orElseThrow(()->new RuntimeException("Discount not found"));

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    UserDiscount userDiscount = new UserDiscount();
    userDiscount.setUser(user);
    userDiscount.setDiscount(discount);
    userDiscount.setStatus(UserDiscountStatus.ACTIVE);
    userDiscount.setActivatedAt(LocalDateTime.now());
    userDiscount.setExpiresAt(discount.getEndsAt());

    userDiscountRepository.save(userDiscount);
}

    public List<DiscountDto> getUserActiveDiscounts(Integer userId) {
        LocalDateTime now = LocalDateTime.now();

        return userDiscountRepository.findByUser_IdAndStatusAndExpiresAtAfter(userId, UserDiscountStatus.ACTIVE, now)
                .stream()
                .map(ud -> toDto(ud.getDiscount()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addDiscount(DiscountAdminRequest discountRequest, String imageUrl) {
    Discount discount = new Discount();

    discount.setTitle(discountRequest.getTitle());
    discount.setDescription(discountRequest.getDescription());
    discount.setStartsAt(discountRequest.getStartsAt());
    discount.setEndsAt(discountRequest.getEndsAt());
    discount.setImageUrl(imageUrl);
    discount.setMinOrderValue(discountRequest.getMinOrderValue());
    discount.setStackable(discountRequest.isStackable());
    discount.setType(discountRequest.getType());
    discount.setValue(discountRequest.getValue());
    discount.setMinOrderValue(discountRequest.getMinOrderValue() != null ? discountRequest.getMinOrderValue() : BigDecimal.ZERO);

    discountRepository.save(discount);

    for(Integer productId: discountRequest.getProductIds()) {
        FoodItem foodItem = foodItemRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("Product not found: " + productId));

        DiscountProduct dp = new DiscountProduct();

        DiscountProductId id = new DiscountProductId();
        id.setDiscountId(discount.getId());
        id.setProductId(productId);

        dp.setId(id);
        dp.setDiscount(discount);
        dp.setFoodItem(foodItem);

        discountProductRepository.save(dp);
    }
    }

    public void deleteDiscount(Long id){
    discountRepository.deleteById(id);
    }

    private DiscountDto toDto(Discount discount) {
        return new DiscountDto(
                discount.getId(),
                discount.getTitle(),
                discount.getDescription(),
                discount.getType().name(),
                discount.getImageUrl(),
                discount.getValue(),
                discount.getStartsAt(),
                discount.getEndsAt(),
                discount.getMinOrderValue(),
                discount.isStackable()
        );
}
}
