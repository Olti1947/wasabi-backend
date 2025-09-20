package com.sushi.wasabi.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Table(name = "users")
@Entity
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;

    private String username;

    private String password;
}
