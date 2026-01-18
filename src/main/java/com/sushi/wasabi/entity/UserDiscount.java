package com.sushi.wasabi.entity;

import com.sushi.wasabi.enums.UserDiscountStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "user_discounts",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "discount_id"})
)
@Getter
@Setter
@NoArgsConstructor
public class UserDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @Column(nullable = false)
    private LocalDateTime activatedAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserDiscountStatus status;

    private LocalDateTime usedAt;

}
