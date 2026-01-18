package com.sushi.wasabi.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class DiscountProductId implements Serializable {
        private Long discountId;
        private Integer productId;

}
