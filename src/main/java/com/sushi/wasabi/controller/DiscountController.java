package com.sushi.wasabi.controller;


import com.sushi.wasabi.dto.ActivateDiscountRequest;
import com.sushi.wasabi.dto.DiscountDto;
import com.sushi.wasabi.entity.User;
import com.sushi.wasabi.services.DiscountService;
import com.sushi.wasabi.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;
    private final JwtService jwtService;

    // 1️⃣ List all available discounts
    @GetMapping("/available")
    public ResponseEntity<List<DiscountDto>> getAvailableDiscounts() {
        Integer userId = jwtService.getCurrentUserId();
        List<DiscountDto> discounts = discountService.getAvailableDiscounts(userId);
        return ResponseEntity.ok(discounts);
    }

    // 2️⃣ Activate a discount for a user
    @PostMapping("/activate")
    public ResponseEntity<Void> activateDiscount(@RequestBody ActivateDiscountRequest request) {
        Integer userId = jwtService.getCurrentUserId();
        discountService.activateDiscount(userId, request.getDiscountId());
        return ResponseEntity.ok().build();
    }

    // 3️⃣ List user’s active discounts
    @GetMapping("/user-active")
    public ResponseEntity<List<DiscountDto>> getUserActiveDiscounts() {
        Integer userId = jwtService.getCurrentUserId();
        List<DiscountDto> discounts = discountService.getUserActiveDiscounts(userId);
        return ResponseEntity.ok(discounts);
    }
}
