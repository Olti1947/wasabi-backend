package com.sushi.wasabi.controller;

import com.sushi.wasabi.entity.User;
import com.sushi.wasabi.services.AdminCodeScanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "User Info", description = "Operations for getting user expense amount")
public class UserInfoController {

    private final AdminCodeScanService adminCodeScanService;
    @Operation(
            summary = "Get Spending",
            description = "Returns specific user total spending"
    )
    @GetMapping("/me/spending")
    public ResponseEntity<BigDecimal> getSpending(@AuthenticationPrincipal User user) {
        BigDecimal spending = adminCodeScanService.getSpendingAmount(user.getId());
        return ResponseEntity.ok(spending);
    }
}
