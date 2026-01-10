package com.sushi.wasabi.entity;

import com.sushi.wasabi.config.StringListJsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private double price;
    private String imageUrl;

    @Column(columnDefinition = "jsonb")
    @Convert(converter = StringListJsonConverter.class)
    private List<String> ingredients;
}
