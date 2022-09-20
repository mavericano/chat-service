package io.dice.chatservice.service;

import io.dice.chatservice.dto.MessageDto;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    List<MessageDto> getAllMessagesPaginated(UUID uuid, int page, int size);

    MessageDto getLatestByChatId(UUID uuid);

    MessageDto saveMessage(String text, UUID chatId, UUID senderId);

    long countAllByChat(UUID chatId);
}
