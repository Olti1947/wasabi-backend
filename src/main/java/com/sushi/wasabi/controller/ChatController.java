package com.sushi.wasabi.controller;

import com.sushi.wasabi.entity.SupportMessage;
import com.sushi.wasabi.entity.User;
import com.sushi.wasabi.services.ChatService;
import com.sushi.wasabi.services.PushNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Tag(name = "Chat", description = "Operations for managing live chat features")
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;
    private final PushNotificationService pushNotificationService;

    @Operation(
            summary = "Send Support Message",
            description = "Send live chat message to all admin users"
    )
    // 1️⃣ User sends message → broadcast to all admins
    @MessageMapping("/support.send")
    public void userToAdmin(@Payload SupportMessage message, Principal principal) {
       SupportMessage saved =
               chatService.saveUserMessage(message, principal.getName());

        System.out.println("User → Admin: " + message);

        // Broadcast to a topic that all admins subscribe to
        messagingTemplate.convertAndSend("/topic/admin.support", saved);
        chatService.publishAdminUnreadCounts();
    }

    @Operation(
            summary = "Response Support Message",
            description = "Send live chat message to specific user"
    )
    // 2️⃣ Admin replies → send only to specific user
    @MessageMapping("/support.reply")
    public void adminToUser(@Payload SupportMessage message, Principal principal) {
        if(principal instanceof UsernamePasswordAuthenticationToken auth
        && auth.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ADMIN"))){
            throw new IllegalArgumentException("Not authorized");
        }

        SupportMessage saved =
                chatService.saveAdminReply(message, "ADMIN");


        System.out.println("Admin -> User: " + message);

        // Send to specific user (the user must be authenticated in WS)
        // Use /user/{username}/queue/support
        if (message.getTo() != null) {
            messagingTemplate.convertAndSendToUser(
                    message.getTo(),               // username of the target user
                    "/queue/support",             // destination on user's side
                    saved
            );
            pushNotificationService.sendToUserDevices(message.getTo(), "New message", message.getContent(), "chat-message");
        } else {
            System.out.println("⚠️ No target user set in message!");
        }
    }
}
