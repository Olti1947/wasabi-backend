package com.sushi.wasabi.config;

import com.sushi.wasabi.entity.Role;
import com.sushi.wasabi.entity.User;
import com.sushi.wasabi.repository.FoodItemRepository;
import com.sushi.wasabi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Fills an empty database with a demo menu, banners, discounts, a demo customer and some orders.
 * Only active when demo-data.enabled=true (SEED_DEMO_DATA), and only while the menu is still empty.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "demo-data.enabled", havingValue = "true")
public class DemoDataSeeder implements ApplicationRunner {

    static final String DEMO_CUSTOMER_EMAIL = "customer@wasabi.local";
    static final String DEMO_CUSTOMER_PASSWORD = "customer123";

    private final DataSource dataSource;
    private final FoodItemRepository foodItemRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (foodItemRepository.count() > 0) {
            return;
        }

        if (userRepository.findByEmail(DEMO_CUSTOMER_EMAIL).isEmpty()) {
            userRepository.save(User.builder()
                    .firstName("Demo")
                    .lastName("Customer")
                    .email(DEMO_CUSTOMER_EMAIL)
                    .password(passwordEncoder.encode(DEMO_CUSTOMER_PASSWORD))
                    .role(Role.USER)
                    .phone("+1 555 0100")
                    .qrCodeToken(UUID.randomUUID().toString())
                    .spending(BigDecimal.ZERO)
                    .build());
            userRepository.flush();
        }

        new ResourceDatabasePopulator(new ClassPathResource("db/demo/demo-data.sql")).execute(dataSource);
        log.info("Loaded demo data. Demo customer: {} / {}", DEMO_CUSTOMER_EMAIL, DEMO_CUSTOMER_PASSWORD);
    }
}
