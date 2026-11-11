package com.sushi.wasabi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "discount_products")
@Getter
@Setter
public class DiscountProduct {

@EmbeddedId
    private DiscountProductId id;

@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("discountId")
    @JoinColumn(name = "discount_id")
    private Discount discount;

@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private FoodItem foodItem;
}
