package com.sushi.wasabi.controller;

import com.sushi.wasabi.dto.*;
import com.sushi.wasabi.entity.Order;
import com.sushi.wasabi.entity.User;
import com.sushi.wasabi.services.CheckoutService;
import com.sushi.wasabi.services.PushNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
@Tag(name = "Checkout", description = "Operations for managing user checkout page")
public class CheckoutController {
private final CheckoutService checkoutService;
private final PushNotificationService pushNotificationService;

    @Operation(
            summary = "Post checkout",
            description = "Start a checkout process"
    )
@PostMapping
    public ResponseEntity<OrderResponseDto> checkout(
        @AuthenticationPrincipal User user,
        @RequestBody CheckoutRequest request
        ) {
    Order order = checkoutService.checkout(user,request);
    pushNotificationService.sendToAdminDevices("New order","You have a new order from: " + user.getFirstName(), "order");
    return ResponseEntity.ok(OrderResponseDto.from(order));
}

    @Operation(
            summary = "Post available discounts",
            description = "Returns available discounts applicable to specific user cart"
    )
@PostMapping("/availableDiscounts")
public ResponseEntity<List<DiscountDto>> availableDiscounts(
        @AuthenticationPrincipal User user,
        @RequestBody List<CartItemRequest> cartItemRequests
) {
    List<DiscountDto> availableDiscounts = checkoutService.applicableDiscounts(cartItemRequests,user);
    return ResponseEntity.ok(availableDiscounts);
}

    @Operation(
            summary = "Post preview",
            description = "Returns preview of applicable discounts according to cart content"
    )
@PostMapping("/preview")
    public ResponseEntity<CheckoutPreviewDto> preview(
            @AuthenticationPrincipal User user,
            @RequestBody CheckoutRequest request
) {
    CheckoutPreviewDto previewDto = checkoutService.preview(user,request);
    return ResponseEntity.ok(previewDto);
}

    @Operation(
            summary = "Get Current Order",
            description = "Gets latest order of user with order details concatenated"
    )
@GetMapping("/current-order")
    public OrderAdminDto getUserCurrentOrder(
            @AuthenticationPrincipal User user
){
    return checkoutService.getCurrentOrder(user.getId());
}

}
