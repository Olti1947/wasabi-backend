package com.sushi.wasabi.dto;

import com.sushi.wasabi.entity.Order;
import com.sushi.wasabi.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponseDto {
    private Long orderId;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal total;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public static OrderResponseDto from(Order order) {
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .subtotal(order.getSubtotal())
                .discount(order.getDiscountTotal())
                .total(order.getTotal())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
