package com.sushi.wasabi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequest {
    private Integer foodItemId;
    private Integer quantity;
}
