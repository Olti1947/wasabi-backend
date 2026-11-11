package com.sushi.wasabi.controller;

import com.sushi.wasabi.dto.ExpenseRequestDto;
import com.sushi.wasabi.dto.UserInfoAdminDto;
import com.sushi.wasabi.services.AdminCodeScanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("api/admin/qr")
@RequiredArgsConstructor
@Tag(name = "Qr Code", description = "Operations for managing user information through QR code scan")
public class QrCodeController {
    private final AdminCodeScanService adminCodeScanService;

    @Operation(
            summary = "Get User Info",
            description = "Returns user information to admin from QrCode scan"
    )
    @GetMapping("/{qrCode}")
    public UserInfoAdminDto getUserInfo(@PathVariable String qrCode){
        return adminCodeScanService.getUserInfoByCode(qrCode);
    }

    @Operation(
            summary = "Post expense",
            description = "Adds specific expense to user total spendings"
    )
    @PostMapping("/add-expense")
    public ResponseEntity<?> addExpense(@RequestBody ExpenseRequestDto expenseRequestDto){
        try {
            adminCodeScanService.addSpendingAmount(expenseRequestDto);

            return ResponseEntity.ok(
                    Map.of(
                            "success", true,
                            "message", "Expense added successfully"
                    ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "Something went wrong"
            ));
        }
        }
}
