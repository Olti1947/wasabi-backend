package com.sushi.wasabi.services.impl;

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
        public Page<FoodItem> getFoods(int page, int size, String search) {

            PageRequest pageRequest = PageRequest.of(
                    page,
                    size,
                    Sort.by(Sort.Direction.ASC, "name")
            );

            if (search == null || search.trim().isEmpty()) {
                return foodItemRepository.findAll(pageRequest);
            }

            return foodItemRepository.searchFoods(search.toLowerCase(), pageRequest);
        }

    @Override
    public FoodItem getById(Integer id) {
        return foodItemRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(
                "Food item with id: " + id + " not found"
        ));
    }
}