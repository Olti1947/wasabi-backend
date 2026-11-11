package com.sushi.wasabi.repository;

import com.sushi.wasabi.entity.DiscountProduct;
import com.sushi.wasabi.entity.DiscountProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface DiscountProductRepository extends JpaRepository<DiscountProduct, DiscountProductId> {
    boolean existsByIdDiscountIdAndIdProductIdIn(
            Long discountId,
            Collection<Integer> productIds
    );

    List<DiscountProduct> findByIdDiscountId(Long discountId);

    @Query("""
    SELECT dp.id.discountId
    FROM DiscountProduct dp
    WHERE dp.id.discountId IN :discountIds
      AND dp.id.productId IN :productIds
""")
    Set<Long> findApplicableDiscountIds(
            @Param("discountIds") Set<Long> discountIds,
            @Param("productIds") Set<Integer> productIds
    );
}
