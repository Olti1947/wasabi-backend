package com.sushi.wasabi.entity;

import com.sushi.wasabi.dto.OrderItemAdminDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_item_id")
    private FoodItem foodItem;

    private Integer quantity;

    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    public OrderItemAdminDto mapToOrderItemAdminDto(){
        OrderItemAdminDto dto = new OrderItemAdminDto();

        dto.setFoodItemId(this.getFoodItem().getId());
        dto.setQuantity(this.getQuantity());
        dto.setFoodName(this.getFoodItem().getName());
        dto.setUnitPrice(this.getUnitPrice());
        dto.setTotalPrice(this.getTotalPrice());

        return dto;
    }
}
