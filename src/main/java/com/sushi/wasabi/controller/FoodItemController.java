package com.sushi.wasabi.controller;

import com.sushi.wasabi.entity.FoodItem;
import com.sushi.wasabi.services.FoodItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
