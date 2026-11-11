package com.sushi.wasabi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class EditFoodItemResponse {
    private String name;
    private String description;
    private Boolean popular;
    private BigDecimal price;
    private List<String> ingredients;
}
