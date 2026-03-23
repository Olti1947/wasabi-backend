package com.sushi.wasabi.controller;

import com.sushi.wasabi.entity.SupportMessage;
import com.sushi.wasabi.services.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Tag(name = "Chat", description = "Operations for managing chat history")
public class ChatRestController {
private final ChatService chatService;

    @Operation(
            summary = "Get Message History",
            description = "Get specific user chat message history"
    )
@GetMapping("/history")
    public List<SupportMessage> history(Principal principal){
    return chatService.getConversation(principal.getName());
}

    @Operation(
            summary = "Post read",
            description = "Mark a chat as read"
    )
@PostMapping("/read")
    public void markRead(Principal principal){
    chatService.markAsRead(principal.getName());
}


    @Operation(
            summary = "Get Admin Inbox",
            description = "Get all stored chats from users"
    )
@GetMapping("/admin/inbox")
    public List<String> adminInbox(){
    return chatService.getUsersWhoMessagedSupport();
}

    @Operation(
            summary = "Get Specific User Inbox",
            description = "Get chat history with a specific user"
    )
@GetMapping("/admin/conversation/{username}")
    public List<SupportMessage> conversation(@PathVariable String username) {
    return chatService.getConversationWithUser(username);
}

    @Operation(
            summary = "Get Unread Count",
            description = "Get unread messages count"
    )
@GetMapping("/admin/unread-count")
    public Map<String, Long> getAdminUnreadCounts(){
    return chatService.getUnreadCountPerUser();
}

    @Operation(
            summary = "Mark read",
            description = "Mark specific user chat as read."
    )
@PostMapping("/admin/mark-read/{username}")
    public void markUserMessagesRead(@PathVariable String username) {
    chatService.markMessagesFromUserAsRead(username);
}

}
