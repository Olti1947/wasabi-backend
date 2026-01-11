package com.sushi.wasabi.entity;

import java.math.BigDecimal;

public record DiscountResult (
    Discount discount,
    UserDiscount userDiscount,
    BigDecimal discountAmount
){
}
