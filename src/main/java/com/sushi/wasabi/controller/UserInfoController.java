package com.sushi.wasabi.controller;

import com.sushi.wasabi.dto.*;
import com.sushi.wasabi.entity.Address;
import com.sushi.wasabi.entity.User;
import com.sushi.wasabi.services.AdminCodeScanService;
import com.sushi.wasabi.services.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "User Info", description = "Operations for getting user expense amount")
public class UserInfoController {

    private final AdminCodeScanService adminCodeScanService;
    private final UserInfoService userInfoService;

    @Operation(
            summary = "Get Spending",
            description = "Returns specific user total spending"
    )
    @GetMapping("/me/spending")
    public ResponseEntity<BigDecimal> getSpending(@AuthenticationPrincipal User user) {
        BigDecimal spending = adminCodeScanService.getSpendingAmount(user.getId());
        return ResponseEntity.ok(spending);
    }

    @GetMapping("/me")
    public UserDto getInfo(@AuthenticationPrincipal User user){
        return userInfoService.getPersonalInfo(user.getId());
    }

    @PostMapping("/me/edit")
    public ResponseEntity<ApiResponse<Void>> editUser(
            @AuthenticationPrincipal User user,
            @RequestBody EditUserRequest request){
        userInfoService.editUser(user.getId(), request);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "User edited successfully",
                null
        ));
    }

    @GetMapping("/me/addresses")
    public List<AddressDto> getAllAddresses(
            @AuthenticationPrincipal User user
    ){
        return userInfoService.getAllAddresses(user.getId());
    }

    @GetMapping("/me/default-address")
    public AddressDto getDefaultAddress(
            @AuthenticationPrincipal User user
    ) {
        return userInfoService.getDefaultAddress(user.getId());
    }

    @PostMapping("/me/address")
    public ResponseEntity<ApiResponse<Void>> addAddress(
            @AuthenticationPrincipal User user,
            @RequestBody AddressRequest request
            ) {
        userInfoService.addAddress(request, user.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Address added successfully", null));
    }

    @DeleteMapping("/me/address/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @PathVariable Long id
    ){
        userInfoService.deleteAddress(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Address deleted successfully",
                null
        ));
    }
}
