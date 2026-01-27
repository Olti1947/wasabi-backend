package com.sushi.wasabi.repository;

import com.sushi.wasabi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
        SELECT DISTINCT o FROM Order o
        LEFT JOIN FETCH o.items i
        LEFT JOIN FETCH i.foodItem
        LEFT JOIN FETCH o.discounts d
        LEFT JOIN FETCH d.discount
        LEFT JOIN FETCH o.user
        ORDER BY o.createdAt DESC
    """)
    List<Order> findAllWithDetails();

    @Query("""
        SELECT o FROM Order o
        LEFT JOIN FETCH o.items i
        LEFT JOIN FETCH i.foodItem
        LEFT JOIN FETCH o.discounts d
        LEFT JOIN FETCH d.discount
        LEFT JOIN FETCH o.user
        WHERE o.id = :orderId
    """)
    Optional<Order> findByIdWithDetails(Long orderId);
}

