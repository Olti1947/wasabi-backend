package com.sushi.wasabi.entity;

import com.sushi.wasabi.dto.OrderDiscountAdminDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_discounts")
@Getter
@Setter
public class OrderDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    private BigDecimal discountAmount;

    public OrderDiscountAdminDto mapToAdminDto(){
        OrderDiscountAdminDto dto = new OrderDiscountAdminDto();
        dto.setDiscountId(this.getId());
        dto.setDiscountName(this.getDiscount().getTitle());
        dto.setDiscountAmount(this.getDiscountAmount());

        return dto;
    }
}
