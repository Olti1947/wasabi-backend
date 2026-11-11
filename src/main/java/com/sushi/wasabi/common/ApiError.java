package com.sushi.wasabi.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ApiError {
    private String message;
    private int status;
    private String path;
    private Instant timestamp;
}
