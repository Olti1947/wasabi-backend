package com.sushi.wasabi.dto;

import com.sushi.wasabi.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String qrCodeToken;
    private BigDecimal spending;
}