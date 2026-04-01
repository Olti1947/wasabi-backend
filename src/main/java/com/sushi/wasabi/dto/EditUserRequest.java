package com.sushi.wasabi.dto;

import lombok.Data;

@Data
public class EditUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
