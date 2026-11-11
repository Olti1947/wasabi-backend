package com.sushi.wasabi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemAdminDto {
private Integer foodItemId;
private String foodName;
private Integer quantity;
private BigDecimal unitPrice;
private BigDecimal totalPrice;
}
