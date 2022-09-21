package io.dice.chatservice.service;

import io.dice.chatservice.dto.ChatDto;
import io.dice.chatservice.dto.UserDto;
import io.dice.chatservice.entity.Chat;
import lombok.SneakyThrows;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.UUID;

public interface ChatService {
    List<ChatDto> getAllChatsPaginatedForUser(UUID uuid, int page, int size);

    ChatDto createChat(List<String> participants, String chatId);

    ChatDto modifyChat(List<String> participants, UUID chatId);

    Chat getChatEntityById(UUID chatId);

    List<UserDto> getMembersForChat(UUID chatId);

    long countAllByUser(UUID userId);

    @SneakyThrows
    void handleMessageAndGetLinkedSessions(WebSocketSession session, String message);

    void removeSession(WebSocketSession session);
}
