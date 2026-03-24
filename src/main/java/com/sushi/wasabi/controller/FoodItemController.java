package com.sushi.wasabi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sushi.wasabi.dto.*;
import com.sushi.wasabi.entity.FoodItem;
import com.sushi.wasabi.services.FoodItemService;
import com.sushi.wasabi.services.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/foods")
@RequiredArgsConstructor
@Tag(name = "Food Items", description = "Operations for managing menu food items")
public class FoodItemController {
private final FoodItemService foodItemService;
private final ImageService imageService;


    @Operation(
            summary = "Get all foods",
            description = "Returns paginated all foods"
    )
@GetMapping
public Page<FoodItemDto> getAllFoods(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String search
){
            return foodItemService.getFoods(page,size,search);
}

    @Operation(
            summary = "Get Specific Food Item",
            description = "Returns specific food item"
    )
@GetMapping("/{id}")
public FoodItem getFoodById(@PathVariable Integer id){
    return foodItemService.getById(id);
}

    @GetMapping("/summary/{id}")
    public EditFoodItemResponse getFoodSummaryById(@PathVariable Integer id){
        return foodItemService.getEditSummary(id);
    }


    @Operation(
            summary = "Post new food",
            description = "Add a new food item to menu as an admin."
    )

    @GetMapping("/popular")
    public List<FoodItemDto> getPopularFood(){
        return foodItemService.getPopularFoods();
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

    @Operation(
            summary = "Delete food item",
            description = "Delete specific food item as an admin"
    )

    @PutMapping(value = "/admin/food")
    public ResponseEntity<ApiResponse<Void>> editFoodItem(
            @RequestBody EditFoodItemDto editFoodItemDto
            ){
        foodItemService.editFoodItem(editFoodItemDto);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Edited food item successfully", null)
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

