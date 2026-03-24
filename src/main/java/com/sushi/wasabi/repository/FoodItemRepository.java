package com.sushi.wasabi.repository;

import com.sushi.wasabi.entity.FoodItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Integer> {
    @Query("""
    SELECT f FROM FoodItem f
    WHERE (
    LOWER(COALESCE(f.name, '')) LIKE %:search%
       OR LOWER(COALESCE(f.description, '')) LIKE %:search%
    )
       AND f.deletedAt IS NULL
""")
    Page<FoodItem> searchFoods(
            @Param("search") String search,
            Pageable pageable
    );

    List<FoodItem> findByIdInAndDeletedAtIsNull(Set<Integer> ids);

    Optional<FoodItem> findByIdAndDeletedAtIsNull(Integer id);

    Page<FoodItem> findByDeletedAtIsNull(Pageable pageable);

    List<FoodItem> findAllByPopularTrue();

    void deleteById(Integer id);
}
