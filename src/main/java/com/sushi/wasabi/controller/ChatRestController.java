package com.sushi.wasabi.controller;

import com.sushi.wasabi.entity.SupportMessage;
import com.sushi.wasabi.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRestController {
private final ChatService chatService;

@GetMapping("/history")
    public List<SupportMessage> history(Principal principal){
    return chatService.getConversation(principal.getName());
}

@PostMapping("/read")
    public void markRead(Principal principal){
    chatService.markAsRead(principal.getName());
}

@GetMapping("/admin/inbox")
    public List<String> adminInbox(){
    return chatService.getUsersWhoMessagedSupport();
}

@GetMapping("/admin/conversation/{username}")
    public List<SupportMessage> conversation(@PathVariable String username) {
    return chatService.getConversationWithUser(username);
}

@GetMapping("/admin/unread-count")
    public Map<String, Long> getAdminUnreadCounts(){
    return chatService.getUnreadCountPerUser();
}

@PostMapping("/admin/mark-read/{username}")
    public void markUserMessagesRead(@PathVariable String username) {
    chatService.markMessagesFromUserAsRead(username);
}

}
