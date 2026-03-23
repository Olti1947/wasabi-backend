package com.sushi.wasabi.controller;

import com.sushi.wasabi.dto.AuthenticationRequest;
import com.sushi.wasabi.dto.AuthenticationResponse;
import com.sushi.wasabi.dto.RegisterRequest;
import com.sushi.wasabi.dto.TokenRequest;
import com.sushi.wasabi.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth-service")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "Operations for managing authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(
            summary = "Register",
            description = "Create a new user account"
    )
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Parameter(description = "User registration request containing email, password and other details")
            @RequestBody RegisterRequest registerRequest) {
        AuthenticationResponse res = authenticationService.register(registerRequest);
        log.info("New user registered: {}", registerRequest.getEmail());
        return ResponseEntity.ok(res);
    }

    @Operation(
            summary = "Authenticate",
            description = "Log in with user / admin account"
    )
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Parameter(
                    description = "Authentication request with user credentials",
                    example = "{ \"email\": \"user@gmail.com\", \"password\": \"user\" }"
            )
            @RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse res = authenticationService.authenticate(authenticationRequest);
        log.info("User authenticated: {}", authenticationRequest.getEmail());
        return ResponseEntity.ok(res);
    }

    @Operation(
            summary = "Refresh Token",
            description = "Use refresh token to prolong session"
    )
    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(
            @Parameter(
                    description = "Refresh token used to obtain a new access token",
                    example = "{ \"token\": \"your-refresh-token-here\" }"
            )
            @RequestBody TokenRequest refreshToken) {
        AuthenticationResponse res = authenticationService.refreshToken(refreshToken.getToken());
        return ResponseEntity.ok(res);
    }

    @Operation(
            summary = "Validate token",
            description = "Validate jwt token"
    )
    @GetMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(
            @Parameter(
                    description = "JWT token to validate",
                    example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            )
            @RequestParam("token") String token) {
        Boolean res = authenticationService.validateToken(token);
        return ResponseEntity.ok(res);
    }
}