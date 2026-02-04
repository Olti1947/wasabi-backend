package com.sushi.wasabi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfoAdminDto {
private int id;
private String email;
private String firstName;
private String lastName;
private List<DiscountDto> discounts;
}
