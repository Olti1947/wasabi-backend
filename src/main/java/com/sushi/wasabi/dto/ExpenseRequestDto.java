package com.sushi.wasabi.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExpenseRequestDto {
private Integer userId;
private BigDecimal amount;
}
