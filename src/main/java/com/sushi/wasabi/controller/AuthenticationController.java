package com.sushi.wasabi.controller;

import com.sushi.wasabi.dto.AuthenticationRequest;
import com.sushi.wasabi.dto.AuthenticationResponse;
import com.sushi.wasabi.dto.RegisterRequest;
import com.sushi.wasabi.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth-service")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        AuthenticationResponse res = authenticationService.register(registerRequest);
        log.info("New user registered: {}", registerRequest.getEmail());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse res = authenticationService.authenticate(authenticationRequest);
        log.info("User authenticated: {}", authenticationRequest.getEmail());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestParam("token") String refreshToken) {
        AuthenticationResponse res = authenticationService.refreshToken(refreshToken);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(@RequestParam("token") String token) {
        Boolean res = authenticationService.validateToken(token);
        return ResponseEntity.ok(res);
    }
}
