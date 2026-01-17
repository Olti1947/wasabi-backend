package com.sushi.wasabi.repository;

import com.sushi.wasabi.entity.UserDiscount;
import com.sushi.wasabi.enums.UserDiscountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserDiscountRepository extends JpaRepository<UserDiscount, Long> {
    Optional<UserDiscount> findByUser_IdAndDiscount_IdAndStatus(
            Integer userId,
            Long discountId,
            UserDiscountStatus status
    );

    boolean existsByUser_IdAndDiscount_Id(Integer userId, Long discountId);

    List<UserDiscount> findByUser_IdAndStatusAndExpiresAtAfter(Integer userId, UserDiscountStatus status, LocalDateTime now);
}
