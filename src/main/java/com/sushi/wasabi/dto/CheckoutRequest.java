package com.sushi.wasabi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckoutRequest {
    private List<CartItemRequest> items;
    private Long discountId;
}
