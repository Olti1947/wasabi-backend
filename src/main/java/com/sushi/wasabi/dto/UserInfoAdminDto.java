package com.sushi.wasabi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class UserInfoAdminDto {
private int id;
private String email;
private String firstName;
private String lastName;
private BigDecimal spending;
private List<DiscountDto> discounts;
}
