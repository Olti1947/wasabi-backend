package com.sushi.wasabi.repository;

import com.sushi.wasabi.dto.DiscountDto;
import com.sushi.wasabi.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    @Query("SELECT d FROM Discount d WHERE d.active = true AND d.startsAt <= :now AND d.endsAt >= :now")
    List<Discount> findAllActive(LocalDateTime now);}
