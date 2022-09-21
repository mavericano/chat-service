package io.dice.chatservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ChatDto {
    private UUID uuid;
    private MessageDto lastMessage;
    private long unreadMessages;
    private String chatName;
}