package io.dice.chatservice.mapper;

import io.dice.chatservice.dto.ChatDto;
import io.dice.chatservice.dto.MessageDto;
import io.dice.chatservice.entity.Chat;
import io.dice.chatservice.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {

    public ChatDto chatToChatDto(Chat chat, MessageDto messageDto, long unreadMessages) {
        return ChatDto.builder()
                .uuid(chat.getChatId())
                .lastMessage(messageDto)
                .unreadMessages(unreadMessages)
                .build();
    }
}
