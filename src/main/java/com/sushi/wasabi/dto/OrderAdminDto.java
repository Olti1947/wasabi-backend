package com.sushi.wasabi.dto;

import com.sushi.wasabi.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderAdminDto {
    private Long orderId;
    private Integer userId;
    private String userEmail;

    private BigDecimal subtotal;
    private BigDecimal discountTotal;
    private BigDecimal total;

    private OrderStatus orderStatus;
    private LocalDateTime createdAt;

    private String phoneNumber;
    private String address;

    private List<OrderItemAdminDto> items;
    private List<OrderDiscountAdminDto> discounts;
}
