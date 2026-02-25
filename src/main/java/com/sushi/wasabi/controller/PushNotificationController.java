package com.sushi.wasabi.controller;

import com.sushi.wasabi.dto.RegisterPushTokenDto;
import com.sushi.wasabi.entity.User;
import com.sushi.wasabi.services.PushNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;

    @PostMapping("/register")
    public void registerToken (
            @RequestBody RegisterPushTokenDto pushTokenDto,
            Principal principal
            ) {
            pushNotificationService.registerToken(principal.getName(), pushTokenDto.getToken(), pushTokenDto.getDeviceType());
    }


    @PostMapping
    public void sendNotification(
            @RequestParam String deviceToken,
            @RequestParam String title,
            @RequestParam String body,
            @RequestParam String eventType
    ){
        pushNotificationService.sendNotification(deviceToken,title,body, eventType);
    }

    @DeleteMapping("/deleteToken")
    public void deleteDeviceToken(
            @RequestParam String token
    ) {
        pushNotificationService.deleteToken(token);
    }
}
