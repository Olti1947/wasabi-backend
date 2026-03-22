package com.sushi.wasabi.entity;

import com.sushi.wasabi.dto.OrderAdminDto;
import com.sushi.wasabi.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> items = new HashSet<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDiscount> discounts = new HashSet<>();

    private BigDecimal subtotal;
    private BigDecimal discountTotal;
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column
    private String comment;

    private LocalDateTime createdAt = LocalDateTime.now();

    public OrderAdminDto mapToAdminDto(){
        OrderAdminDto dto = new OrderAdminDto();

        dto.setOrderId(this.getId());
        dto.setItems(
                this.getItems()
                        .stream()
                        .map(OrderItem::mapToOrderItemAdminDto)
                        .toList()
        );
        dto.setDiscounts(
                this.getDiscounts()
                        .stream()
                        .map(OrderDiscount::mapToAdminDto)
                        .toList()
        );
        dto.setUserEmail(this.getUser().getEmail());
        dto.setSubtotal(this.getSubtotal());
        dto.setDiscountTotal(this.getDiscountTotal());
        dto.setTotal(this.getTotal());
        dto.setOrderStatus(this.getStatus());
        dto.setUserId(this.getUser().getId());
        dto.setCreatedAt(this.getCreatedAt());
        dto.setPhoneNumber(this.getPhoneNumber());
        dto.setComment(this.getComment());
        dto.setAddress(this.getAddress());

        return dto;

    }

}
