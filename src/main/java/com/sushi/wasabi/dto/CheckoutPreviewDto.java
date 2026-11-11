package com.sushi.wasabi.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CheckoutPreviewDto {
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal total;
    private String appliedDiscount;
}
