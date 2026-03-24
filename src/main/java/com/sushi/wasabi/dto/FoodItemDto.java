package com.sushi.wasabi.dto;

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
    private List<String> ingredients;
}
