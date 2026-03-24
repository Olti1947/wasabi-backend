package com.sushi.wasabi.services;

import com.sushi.wasabi.dto.EditFoodItemDto;
import com.sushi.wasabi.dto.EditFoodItemResponse;
import com.sushi.wasabi.dto.FoodItemDto;
import com.sushi.wasabi.dto.FoodItemRequest;
import com.sushi.wasabi.entity.FoodItem;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FoodItemService {
    Page<FoodItemDto> getFoods(int page, int size, String search);

    FoodItem getById(Integer id);

    void addFoodItem(FoodItemRequest foodItemRequest, String imageUrl);

    List<FoodItemDto> getPopularFoods();

    void editFoodItem(EditFoodItemDto foodItemDto);

    EditFoodItemResponse getEditSummary(Integer id);

    void deleteFoodItem(Integer id);
}