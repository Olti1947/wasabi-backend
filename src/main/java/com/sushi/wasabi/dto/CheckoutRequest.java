package com.sushi.wasabi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckoutRequest {
    private List<CartItemRequest> items;
    private String comment;
    private String phoneNumber;
    private String address;
    private Long discountId;
}
