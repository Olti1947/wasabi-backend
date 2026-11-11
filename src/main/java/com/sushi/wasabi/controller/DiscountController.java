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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Discount", description = "Operations for managing user discounts")
public class DiscountController {

    private final DiscountService discountService;
    private final JwtService jwtService;
    private final ImageService imageService;
    private final ObjectMapper objectMapper;

    // 1️⃣ List all available discounts
    @Operation(
            summary = "Get Available Discounts",
            description = "Returns all available discounts for a user"
    )
    @GetMapping("/available")
    public ResponseEntity<List<DiscountDto>> getAvailableDiscounts() {
        Integer userId = jwtService.getCurrentUserId();
        List<DiscountDto> discounts = discountService.getAvailableDiscounts(userId);
        return ResponseEntity.ok(discounts);
    }

    // 2️⃣ Activate a discount for a user
    @Operation(
            summary = "Activate discounts",
            description = "Activates specific discount for a user"
    )
    @PostMapping("/activate")
    public ResponseEntity<Void> activateDiscount(@RequestBody ActivateDiscountRequest request) {
        Integer userId = jwtService.getCurrentUserId();
        discountService.activateDiscount(userId, request.getDiscountId());
        return ResponseEntity.ok().build();
    }

    // 3️⃣ List user’s active discounts
    @Operation(
            summary = "Get User Active Discounts",
            description = "Returns all active discounts of that user"
    )
    @GetMapping("/user-active")
    public ResponseEntity<List<DiscountDto>> getUserActiveDiscounts() {
        Integer userId = jwtService.getCurrentUserId();
        List<DiscountDto> discounts = discountService.getUserActiveDiscounts(userId);
        return ResponseEntity.ok(discounts);
    }

    @Operation(
            summary = "Post new discount",
            description = "Post a new discount as an admin"
    )
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

    @Operation(
            summary = "Delete discount",
            description = "Delete specific discount as an admin"
    )
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscount(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Use discount",
            description = "Mark a discount used for a user"
    )
    @PostMapping("/admin/use")
    public ResponseEntity<Void> useDiscount (@RequestBody AdminUseDiscountDto useDiscountDto) {
        discountService.useDiscount(useDiscountDto.getUserId(), useDiscountDto.getDiscountId());
        return ResponseEntity.ok().build();
    }
}
