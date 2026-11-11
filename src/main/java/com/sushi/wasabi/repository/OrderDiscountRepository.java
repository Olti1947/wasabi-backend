package com.sushi.wasabi.repository;

import com.sushi.wasabi.entity.OrderDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDiscountRepository extends JpaRepository<OrderDiscount, Long> {
}
