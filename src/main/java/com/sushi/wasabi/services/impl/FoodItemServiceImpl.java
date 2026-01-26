package com.sushi.wasabi.services.impl;

import com.sushi.wasabi.dto.FoodItemDto;
import com.sushi.wasabi.dto.FoodItemRequest;
import com.sushi.wasabi.entity.FoodItem;
import com.sushi.wasabi.repository.FoodItemRepository;
import com.sushi.wasabi.services.FoodItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodItemServiceImpl implements FoodItemService {

        private final FoodItemRepository foodItemRepository;

        @Override
        public Page<FoodItemDto> getFoods(int page, int size, String search) {

            PageRequest pageRequest = PageRequest.of(
                    page,
                    size,
                    Sort.by(Sort.Direction.ASC, "name")
            );

            Page<FoodItem> foods;
            if (search == null || search.trim().isEmpty()) {
                foods = foodItemRepository.findAll(pageRequest);
            } else {
                foods = foodItemRepository.searchFoods(search.toLowerCase(), pageRequest);
            }
            return foods.map(food -> food.toDto()
            );

        }

    @Override
    public FoodItem getById(Integer id) {
        return foodItemRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(
                "Food item with id: " + id + " not found"
        ));
    }

    @Override
    public void addFoodItem(FoodItemRequest foodItemRequest, String imageUrl) {
        FoodItem foodItem = new FoodItem();

        foodItem.setName(foodItemRequest.getName());
        foodItem.setDescription(foodItemRequest.getDescription());
        foodItem.setImageUrl(imageUrl);
        foodItem.setPrice(foodItemRequest.getPrice());
        foodItem.setIngredients(foodItemRequest.getIngredients());

        foodItemRepository.save(foodItem);
    }

    @Override
    public void deleteFoodItem(Integer id) {
        foodItemRepository.deleteById(id);
    }
}