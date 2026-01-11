package com.sushi.wasabi.repository;

import com.sushi.wasabi.entity.UserDiscount;
import com.sushi.wasabi.enums.UserDiscountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDiscountRepository extends JpaRepository<UserDiscount, Long> {
    Optional<UserDiscount> findByUserIdAndDiscountIdAndStatus(
            Integer userId,
            Long discountId,
            UserDiscountStatus status
    );
}
