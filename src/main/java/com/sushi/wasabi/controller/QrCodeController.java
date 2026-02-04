package com.sushi.wasabi.controller;

import com.sushi.wasabi.dto.UserInfoAdminDto;
import com.sushi.wasabi.services.AdminCodeScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/qr")
@RequiredArgsConstructor
public class QrCodeController {
    private final AdminCodeScanService adminCodeScanService;

    @GetMapping("/{qrCode}")
    public UserInfoAdminDto getUserInfo(@PathVariable String qrCode){
        return adminCodeScanService.getUserInfoByCode(qrCode);
    }
}
