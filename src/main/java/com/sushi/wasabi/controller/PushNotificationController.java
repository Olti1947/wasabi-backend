package com.sushi.wasabi.controller;

import com.sushi.wasabi.dto.RegisterPushTokenDto;
import com.sushi.wasabi.entity.User;
import com.sushi.wasabi.services.PushNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
@Tag(name = "Push Notifications", description = "Operations for managing push notification tokens")
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;

    @Operation(
            summary = "Register Device Token",
            description = "Registers user device token for push notifications"
    )
    @PostMapping("/register")
    public void registerToken (
            @RequestBody RegisterPushTokenDto pushTokenDto,
            Principal principal
            ) {
            pushNotificationService.registerToken(principal.getName(), pushTokenDto.getToken(), pushTokenDto.getDeviceType());
    }

    @Operation(
            summary = "Post send notification",
            description = "Sends specific notification to specific user(test only purposes)"
    )
    @PostMapping
    public void sendNotification(
            @RequestParam String deviceToken,
            @RequestParam String title,
            @RequestParam String body,
            @RequestParam String eventType
    ){
        pushNotificationService.sendNotification(deviceToken,title,body, eventType);
    }
    @Operation(
            summary = "Delete Device Token",
            description = "Deletes device token from specific user"
    )
    @DeleteMapping("/deleteToken/{token}")
    public void deleteDeviceToken(
            @PathVariable String token
    ) {
        pushNotificationService.deleteToken(token);
    }
}
