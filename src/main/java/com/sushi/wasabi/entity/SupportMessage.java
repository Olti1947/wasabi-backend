package com.sushi.wasabi.entity;

import com.sushi.wasabi.enums.MessageType;
import lombok.Data;

import java.util.Date;

@Data
public class SupportMessage {
    private Long id;
    private String content;
    private String from;
    private String to;
    private MessageType messageType;
    private Date timestamp;
    private boolean read;
}
