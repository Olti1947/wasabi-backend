package com.sushi.wasabi.config;

import com.sushi.wasabi.entity.Role;
import com.sushi.wasabi.entity.User;
import com.sushi.wasabi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Creates a default admin account on startup when the database has no admin yet.
 * Credentials come from ADMIN_EMAIL / ADMIN_PASSWORD; seeding is skipped if no password is set.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AdminSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(ApplicationArguments args) {
        if (!userRepository.findAllByRole(Role.ADMIN).isEmpty()) {
            return;
        }
        if (adminPassword == null || adminPassword.isBlank()) {
            log.warn("No admin user exists and ADMIN_PASSWORD is not set. Skipping default admin creation.");
            return;
        }

        // Promote an existing account with that email instead of failing on the unique constraint.
        User admin = userRepository.findByEmail(adminEmail).orElseGet(() -> User.builder()
                .firstName("Admin")
                .lastName("Wasabi")
                .email(adminEmail)
                .qrCodeToken(UUID.randomUUID().toString())
                .spending(BigDecimal.ZERO)
                .build());
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setRole(Role.ADMIN);

        userRepository.save(admin);
        log.info("Created default admin user '{}'.", adminEmail);
    }
}
