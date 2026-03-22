package com.sushi.wasabi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sushi.wasabi.dto.ApiResponse;
import com.sushi.wasabi.dto.FoodItemDto;
import com.sushi.wasabi.dto.FoodItemRequest;
import com.sushi.wasabi.entity.FoodItem;
import com.sushi.wasabi.services.FoodItemService;
import com.sushi.wasabi.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/foods")
@RequiredArgsConstructor
public class FoodItemController {
private final FoodItemService foodItemService;
private final ImageService imageService;

@GetMapping
public Page<FoodItemDto> getAllFoods(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String search
){
            return foodItemService.getFoods(page,size,search);
}

@GetMapping("/{id}")
public FoodItem getFoodById(@PathVariable Integer id){
    return foodItemService.getById(id);
}

@PostMapping(value = "/admin/food",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
)
    public ResponseEntity<ApiResponse<Void>> addFoodItem(
        @RequestPart("data") String data,
        @RequestPart("image")MultipartFile image
        ) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    FoodItemRequest food = mapper.readValue(data, FoodItemRequest.class);
    String imageUrl = imageService.uploadFoodImage(image);
    foodItemService.addFoodItem(food, imageUrl);
    return ResponseEntity.ok(
            new ApiResponse<>(true, "Added food item successfully", null)
    );
}

@DeleteMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFoodItem(@PathVariable Integer id) {
    foodItemService.deleteFoodItem(id);
    return ResponseEntity.ok(
            new ApiResponse<>(true, "Item deleted successfully", null)
    );
}
}

