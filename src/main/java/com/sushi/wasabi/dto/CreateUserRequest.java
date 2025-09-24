package com.sushi.wasabi.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CreateUserRequest {
private String username;
private String email;
private String password;
}
