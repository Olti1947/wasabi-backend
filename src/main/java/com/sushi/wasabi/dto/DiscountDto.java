package com.sushi.wasabi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DiscountDto {
    private Long id;
    private String title;
    private String description;
    private String type; // PERCENTAGE or FIXED
    private String imageUrl;
    private BigDecimal value;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private BigDecimal minOrderValue;
    private boolean stackable;
}