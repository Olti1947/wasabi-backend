package com.sushi.wasabi.services;

import com.sushi.wasabi.entity.ChatMessageEntity;
import com.sushi.wasabi.entity.SupportMessage;
import com.sushi.wasabi.mapper.ChatMessageMapper;
import com.sushi.wasabi.repository.ChatMessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository repository;
    private final ChatMessageMapper mapper;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public SupportMessage saveUserMessage(
            SupportMessage dto,
            String fromUser
    ) {
        ChatMessageEntity entity = mapper.toEntity(dto, fromUser, "ADMIN");
        repository.save(entity);
        return mapper.toDto(entity);
    }

    public SupportMessage saveAdminReply(
            SupportMessage dto,
            String adminUser
    ) {
        ChatMessageEntity entity =
                mapper.toEntity(dto, adminUser, dto.getTo());

        repository.save(entity);
        return mapper.toDto(entity);
    }


    public List<SupportMessage> getConversation(String username){
        return repository.findByFromUserOrToUserOrderByTimestampAsc(username,username)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

public List<SupportMessage> getConversationWithUser(String username){
    return repository.findConversationWithUser(username)
            .stream()
            .map(mapper::toDto)
            .toList();
}

    public List<String> getUsersWhoMessagedSupport(){
        return repository.findDistinctFromUsers();
    }

    public void markAsRead(String username){
        System.out.println("Came to mark all read for: " + username);
        List<ChatMessageEntity> unread =
                repository.findByToUserAndReadFalse(username);

        unread.forEach(m -> m.setRead(true));
        repository.saveAll(unread);
    }

    public Map<String, Long> getUnreadCountPerUser() {
        List<String> users = getUsersWhoMessagedSupport();

        return users.stream()
                .collect(Collectors.toMap(
                        user -> user,
                        user -> repository.countByFromUserAndReadFalse(user)
                ));
    }

    @Transactional
    public void markMessagesFromUserAsRead(String username) {
        List<ChatMessageEntity> messages = repository.findUnreadConvo(username);
        messages.forEach(m -> m.setRead(true));
        repository.saveAll(messages);
    }

    public void publishAdminUnreadCounts() {
        Map<String, Long> unreadCounts = getUnreadCountPerUser();
        simpMessagingTemplate.convertAndSend(
                "/topic/admin.unread-counts",
                unreadCounts
        );
    }
}
