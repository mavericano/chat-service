package io.dice.chatservice.service.impl;

import io.dice.chatservice.dto.MessageDto;
import io.dice.chatservice.entity.Message;
import io.dice.chatservice.mapper.MessageMapper;
import io.dice.chatservice.mapper.UserMapper;
import io.dice.chatservice.repository.ChatRepository;
import io.dice.chatservice.repository.MessageRepository;
import io.dice.chatservice.service.MessageService;
import io.dice.chatservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserService userService;
//    private final ChatService chatService;
    private final MessageMapper messageMapper;
    private final UserMapper userMapper;

    @Override
    public List<MessageDto> getAllMessagesPaginated(UUID chatId, int page, int size) {
        //TODO exception
        final var result = messageRepository.findAllByChatOrderByTimestampDesc(chatRepository.findById(chatId).orElseThrow(), PageRequest.of(page, size));
        return result.stream().map(message -> messageMapper.messageToMessageDto(message, userMapper.userToUserDto(message.getSender()))).collect(Collectors.toList());
    }

    @Override
    public MessageDto getLatestByChatId(UUID uuid) {
        final var msg = messageRepository.findLatestByChatId(uuid.toString()).orElseThrow(RuntimeException::new);
        return messageMapper.messageToMessageDto(msg, userMapper.userToUserDto(msg.getSender()));
    }

    @Override
    public MessageDto saveMessage(String text, UUID chatId, UUID senderId) {
        final var message = Message.builder()
                .uuid(UUID.randomUUID())
                .timestamp(Timestamp.from(Instant.now()))
                .text(text)
                .chat(chatRepository.findById(chatId).orElseThrow(RuntimeException::new))
                .sender(userMapper.userDtoToUser(userService.getUserById(senderId)))
                .build();
        final var returned = messageRepository.save(message);
        return messageMapper.messageToMessageDto(returned, userMapper.userToUserDto(returned.getSender()));
    }

    @Override
    public long countAllByChat(UUID chatId) {
        return messageRepository.countAllByChat(chatRepository.findById(chatId).orElseThrow());
    }
}
