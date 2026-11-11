package com.sushi.wasabi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderDiscountAdminDto {
    private Long discountId;
    private String discountName;
    private BigDecimal discountAmount;
}
