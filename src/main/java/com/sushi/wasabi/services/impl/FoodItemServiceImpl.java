package com.sushi.wasabi.services.impl;

import com.sushi.wasabi.dto.EditFoodItemDto;
import com.sushi.wasabi.dto.EditFoodItemResponse;
import com.sushi.wasabi.dto.FoodItemDto;
import com.sushi.wasabi.dto.FoodItemRequest;
import com.sushi.wasabi.entity.FoodItem;
import com.sushi.wasabi.enums.FoodCategory;
import com.sushi.wasabi.repository.FoodItemRepository;
import com.sushi.wasabi.services.FoodItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodItemServiceImpl implements FoodItemService {

        private final FoodItemRepository foodItemRepository;

        @Override
        public Page<FoodItemDto> getFoods(int page, int size, String search, FoodCategory foodCategory, Boolean baked) {

            PageRequest pageRequest = PageRequest.of(
                    page,
                    size,
                    Sort.by(Sort.Direction.ASC, "name")
            );

            Page<FoodItem> foods;
            if (search != null && !search.trim().isEmpty()) {
                foods = foodItemRepository.searchFoods(search.toLowerCase(), pageRequest);
            }
            else if (foodCategory != null || baked != null) {
                String categoryStr = (foodCategory != null) ? foodCategory.name() : null;
                foods = foodItemRepository.findByCategoryAndBaked(categoryStr, baked, pageRequest);
            }
            else {
                foods = foodItemRepository.findByDeletedAtIsNull(pageRequest);
            }
            return foods.map(FoodItem::toDto
            );

        }

    @Override
    public FoodItem getById(Integer id) {
        return foodItemRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()-> new EntityNotFoundException(
                "Food item with id: " + id + " not found"
        ));
    }

    @Override
    public void addFoodItem(FoodItemRequest foodItemRequest, String imageUrl) {
        FoodItem foodItem = new FoodItem();

        foodItem.setName(foodItemRequest.getName());
        foodItem.setDescription(foodItemRequest.getDescription());
        foodItem.setImageUrl(imageUrl);
        foodItem.setPopular(foodItemRequest.getPopular());
        foodItem.setPrice(foodItemRequest.getPrice());
        foodItem.setCategory(foodItemRequest.getFoodCategory());
        foodItem.setBaked(foodItemRequest.getBaked());
        foodItem.setIngredients(foodItemRequest.getIngredients());

        foodItemRepository.save(foodItem);
    }

    @Override
    public void deleteFoodItem(Integer id) {
        FoodItem foodItem = foodItemRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(
                () -> new RuntimeException("There is no such item")
        );

        foodItem.setDeletedAt(LocalDateTime.now());
        foodItemRepository.save(foodItem);
    }

    @Override
    public List<FoodItemDto> getPopularFoods() {
         return foodItemRepository.findAllByPopularTrue().stream().map(FoodItem::toDto).toList();
    }

    @Override
    public void editFoodItem(EditFoodItemDto foodItemDto) {
        FoodItem foodItem = foodItemRepository.findById(foodItemDto.getId()).orElseThrow(() -> new RuntimeException("No such food item"));
        foodItem.setName(foodItemDto.getName());
        foodItem.setDescription(foodItemDto.getDescription());
        foodItem.setPopular(foodItemDto.getPopular());
        foodItem.setPrice(foodItemDto.getPrice());
        foodItem.setIngredients(foodItemDto.getIngredients());

        foodItemRepository.save(foodItem);
    }

    @Override
    public EditFoodItemResponse getEditSummary(Integer id) {
        EditFoodItemResponse response = new EditFoodItemResponse();
        FoodItem foodItem = foodItemRepository.findById(id).orElseThrow(() -> new RuntimeException("No such food item"));
        response.setName(foodItem.getName());
        response.setDescription(foodItem.getDescription());
        response.setPrice(foodItem.getPrice());
        response.setPopular(foodItem.getPopular());
        response.setIngredients(foodItem.getIngredients());
        return response;
    }
}