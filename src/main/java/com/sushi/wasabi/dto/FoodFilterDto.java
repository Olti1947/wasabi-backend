package com.sushi.wasabi.dto;

import com.sushi.wasabi.enums.FoodCategory;
import lombok.Data;

@Data
public class FoodFilterDto {
    private FoodCategory category;
    private Boolean baked;
}
