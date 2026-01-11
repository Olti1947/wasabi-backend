package com.sushi.wasabi.repository;

import com.sushi.wasabi.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
