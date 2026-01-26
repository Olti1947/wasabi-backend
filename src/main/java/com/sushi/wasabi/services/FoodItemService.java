package com.sushi.wasabi.services;

import com.sushi.wasabi.dto.FoodItemDto;
import com.sushi.wasabi.dto.FoodItemRequest;
import com.sushi.wasabi.entity.FoodItem;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface FoodItemService {
    Page<FoodItemDto> getFoods(int page, int size, String search);

    FoodItem getById(Integer id);

    void addFoodItem(FoodItemRequest foodItemRequest, String imageUrl);

    void deleteFoodItem(Integer id);
}