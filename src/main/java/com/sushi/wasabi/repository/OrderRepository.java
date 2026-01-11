package com.sushi.wasabi.repository;

import com.sushi.wasabi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
