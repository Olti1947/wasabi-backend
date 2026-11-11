package com.sushi.wasabi.dto;

import com.sushi.wasabi.enums.FoodCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class FoodItemDto {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Boolean popular;
    private FoodCategory category;
    private Boolean baked;
    private List<String> ingredients;
}
