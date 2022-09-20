package io.dice.chatservice.mapper;

import io.dice.chatservice.dto.MessageDto;
import io.dice.chatservice.dto.UserDto;
import io.dice.chatservice.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public MessageDto messageToMessageDto(Message message, UserDto userDto) {
        return MessageDto.builder()
                .uuid(message.getUuid())
                .timestamp(message.getTimestamp())
                .text(message.getText())
                .sender(userDto)
                .build();
    }
}
