package com.sushi.wasabi.repository;

import com.sushi.wasabi.entity.FoodItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    @Query("""
        SELECT f FROM FoodItem f
        WHERE LOWER(f.name) LIKE %:search%
           OR LOWER(f.description) LIKE %:search%
    """)
    Page<FoodItem> searchFoods(
            @Param("search") String search,
            Pageable pageable
    );
}
