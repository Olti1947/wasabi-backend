package com.sushi.wasabi.mapper;

import com.sushi.wasabi.entity.ChatMessageEntity;
import com.sushi.wasabi.entity.SupportMessage;
import com.sushi.wasabi.enums.MessageType;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ChatMessageMapper {
    public SupportMessage toDto(ChatMessageEntity entity) {
        SupportMessage dto = new SupportMessage();
        dto.setFrom(entity.getFromUser());
        dto.setTo(entity.getToUser());
        dto.setMessageType(entity.getMessageType());
        dto.setContent(entity.getContent());
        dto.setTimestamp(entity.getTimestamp());
        dto.setRead(entity.isRead());
        dto.setId(entity.getId());

        return dto;
    }

    public ChatMessageEntity toEntity(SupportMessage dto, String fromUser, String toUser) {
        ChatMessageEntity entity = new ChatMessageEntity();
        entity.setFromUser(fromUser);
        entity.setToUser(toUser);
        entity.setContent(dto.getContent());
        entity.setTimestamp(
                dto.getTimestamp() != null
                ? dto.getTimestamp()
                        : new Date()
        );
        entity.setMessageType(dto.getMessageType() != null? dto.getMessageType(): MessageType.TEXT );
        entity.setRead(false);

        return entity;
    }
}
