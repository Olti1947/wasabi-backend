package com.sushi.wasabi.dto;

import com.sushi.wasabi.enums.FoodCategory;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class FoodItemRequest {
    private String name;
    private String description;
    private Boolean popular;
    private BigDecimal price;
    private FoodCategory foodCategory;
    private Boolean baked;
    private List<String> ingredients;

}
