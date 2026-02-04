package com.sushi.wasabi.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sushi.wasabi.dto.ActivateDiscountRequest;
import com.sushi.wasabi.dto.AdminUseDiscountDto;
import com.sushi.wasabi.dto.DiscountAdminRequest;
import com.sushi.wasabi.dto.DiscountDto;
import com.sushi.wasabi.services.DiscountService;
import com.sushi.wasabi.services.ImageService;
import com.sushi.wasabi.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;
    private final JwtService jwtService;
    private final ImageService imageService;
    private final ObjectMapper objectMapper;

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

    @PostMapping(value = "/admin",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addDiscount(
            @RequestPart("data") String data,
            @RequestPart("image")MultipartFile image
            ) throws JsonProcessingException {
        DiscountAdminRequest discount = objectMapper.readValue(data, DiscountAdminRequest.class);
        String imageUrl = imageService.uploadDiscountImage(image);
        discountService.addDiscount(discount,imageUrl);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscount(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/use")
    public ResponseEntity<Void> useDiscount (@RequestBody AdminUseDiscountDto useDiscountDto) {
        discountService.useDiscount(useDiscountDto.getUserId(), useDiscountDto.getDiscountId());
        return ResponseEntity.ok().build();
    }
}
