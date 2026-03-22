package com.sushi.wasabi.services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.sushi.wasabi.entity.DeviceToken;
import com.sushi.wasabi.entity.Role;
import com.sushi.wasabi.entity.User;
import com.sushi.wasabi.repository.DeviceTokenRepository;
import com.sushi.wasabi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PushNotificationService {
    private final DeviceTokenRepository deviceTokenRepository;
    private final UserRepository userRepository;

    public void sendNotification(String deviceToken, String title, String body, String eventType) {
        Message message = Message.builder()
                .setToken(deviceToken)
                .putData("eventType", eventType)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Message sent successfuly: " + response);
        } catch (Exception e) {
            System.out.println("Message failed to send");
            e.printStackTrace();
        }
    }

    @Transactional
    public void registerToken(String userEmail, String token, String deviceType) {

        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Device token must not be null or blank");
        }

        Optional<DeviceToken> existing = deviceTokenRepository.findByToken(token);
        DeviceToken deviceToken = existing.orElseGet(DeviceToken::new);
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("No user with such id"));

        deviceToken.setUser(user);
        deviceToken.setToken(token);
        deviceToken.setDeviceType(deviceType);
        deviceToken.setActive(true);
        deviceToken.setLastUsedAt(LocalDateTime.now());

        deviceTokenRepository.save(deviceToken);

    }

    public void deleteToken(String token){
        DeviceToken deviceToken = deviceTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("No such device token"));

        deviceTokenRepository.delete(deviceToken);

    }

    @Transactional
    public void sendToAdminDevices(String title, String body, String event){
        List<User> adminUsers = userRepository.findAllByRole(Role.ADMIN);

        for(User user : adminUsers){
            List<DeviceToken> userTokens = user.getDeviceTokens();
            for(DeviceToken deviceToken : userTokens) {
                sendNotification(deviceToken.getToken(), title, body, event);
            }
        }

    }

    @Transactional
    public void sendToUserDevices(String userName, String title, String body, String eventType){
        User user = userRepository.findByEmail(userName).orElseThrow(() -> new RuntimeException("User not found"));
        List<DeviceToken> deviceTokens = user.getDeviceTokens();

        for(DeviceToken deviceToken : deviceTokens) {
            sendNotification(deviceToken.getToken(), title, body, eventType);
        }
    }
}
