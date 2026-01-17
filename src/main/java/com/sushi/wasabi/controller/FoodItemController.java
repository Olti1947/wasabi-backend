package com.sushi.wasabi.controller;

import com.sushi.wasabi.entity.FoodItem;
import com.sushi.wasabi.services.FoodItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/foods")
@RequiredArgsConstructor
public class FoodItemController {
private final FoodItemService foodItemService;

@GetMapping
public Page<FoodItem> getAllFoods(
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
}
