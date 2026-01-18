package com.sushi.wasabi.services;

import com.sushi.wasabi.entity.FoodItem;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface FoodItemService {
    Page<FoodItem> getFoods(int page, int size, String search);

    FoodItem getById(Integer id);
}