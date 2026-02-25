package com.sushi.wasabi.dto;

import lombok.Data;

@Data
public class RegisterPushTokenDto {
    private String token;
    private String deviceType;
}
