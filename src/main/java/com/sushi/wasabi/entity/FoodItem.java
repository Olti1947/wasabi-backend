package com.sushi.wasabi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sushi.wasabi.config.StringListJsonConverter;
import com.sushi.wasabi.dto.FoodItemDto;
import com.sushi.wasabi.enums.FoodCategory;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "food_items")
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    private String name;
    private String description;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    private String imageUrl;
    private Boolean popular;
    @Column(columnDefinition = "food_category")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private FoodCategory category;

    private Boolean baked;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<String> ingredients;

    @OneToMany(mappedBy = "foodItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiscountProduct> discountProducts = new ArrayList<>();

    public FoodItemDto toDto(){
        return new FoodItemDto(
                this.getId(),
                this.getName(),
                this.getDescription(),
                this.getPrice(),
                this.getImageUrl(),
                this.getPopular(),
                this.getCategory(),
                this.getBaked(),
                this.getIngredients()
        );
    }
}
