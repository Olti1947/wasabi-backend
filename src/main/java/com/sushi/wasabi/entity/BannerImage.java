package com.sushi.wasabi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "banner_images")
@Getter
@Setter
public class BannerImage {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    private String description;

    @Column(name = "is_active")
    private Boolean active;
}
