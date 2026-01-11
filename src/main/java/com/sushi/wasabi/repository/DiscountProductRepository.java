package com.sushi.wasabi.repository;

import com.sushi.wasabi.entity.DiscountProduct;
import com.sushi.wasabi.entity.DiscountProductId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface DiscountProductRepository extends JpaRepository<DiscountProduct, DiscountProductId> {
    boolean existsByIdDiscountIdAndProductIdIn(
            Long discountId,
            Collection<Integer> productIds
    );

    List<DiscountProduct> findByIdDiscountId(Long discountId);
}
