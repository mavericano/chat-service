package io.dice.chatservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dice.chatservice.dto.ChatDto;
import io.dice.chatservice.dto.UserDto;
import io.dice.chatservice.entity.Chat;
import io.dice.chatservice.entity.Session;
import io.dice.chatservice.entity.UserChatCounter;
import io.dice.chatservice.mapper.ChatMapper;
import io.dice.chatservice.mapper.UserMapper;
import io.dice.chatservice.repository.ChatRepository;
import io.dice.chatservice.repository.UserChatCounterRepository;
import io.dice.chatservice.service.ChatService;
import io.dice.chatservice.service.MessageService;
import io.dice.chatservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;
    private final ChatMapper chatMapper;
    private final UserChatCounterRepository userChatCounterRepository;
    private final UserMapper userMapper;
    private final MessageService messageService;
    private final EntityManager entityManager;
    private List<Session> chatSessions = new ArrayList<>();
    private List<Session> chatListSessions = new ArrayList<>();

    @Override
    public List<ChatDto> getAllChatsPaginatedForUser(UUID uuid, int page, int size) {
        return chatRepository.findAllPaginatedByUserId(uuid.toString(), size*page, size).stream()
                .map(chat -> chatMapper.chatToChatDto(chat, messageService.getLatestByChatId(chat.getChatId()), userChatCounterRepository.findByChatChatIdAndUserUuid(chat.getChatId(), uuid).orElseThrow().getCounter()))
                .collect(Collectors.toList());
//        return null;
    }

    @Override
    public ChatDto createChat(List<String> participants, String chatId, String chatName) {
        final var chat = Chat.builder()
                .chatId(UUID.fromString(chatId))
                .messages(Set.of())
                .participants(null)
                .chatName(chatName)
                .build();
        final var users = participants.stream()
                .map(UUID::fromString)
                .map(userService::getUserById)
                .map(userMapper::userDtoToUser)
                .map(user -> UserChatCounter.builder()
                        .uuid(UUID.randomUUID())
                        .user(user)
                        .chat(chat)
                        .counter(0)
                        .build())
                .collect(Collectors.toSet());
        chat.setParticipants(users);
//        userChatCounterRepository.saveAll(users);
        Chat returned = chatRepository.save(chat);
//        TODO not null
        return chatMapper.chatToChatDto(returned, messageService.getLatestByChatId(returned.getChatId()), 0);
    }

    @Override
    @Deprecated
    public ChatDto modifyChat(List<String> newParticipants, UUID chatId) {
        final var newList = new ArrayList<>();
        final var chat = chatRepository.findById(chatId).orElseThrow(RuntimeException::new);
        final var currentParticipants = chat.getParticipants().stream()
                .map(UserChatCounter::getUser)
                .collect(Collectors.toList());
        final var users = newParticipants.stream()
                .map(UUID::fromString)
                .map(id -> userChatCounterRepository.findByUserUuid(id).orElseGet(() -> UserChatCounter.builder()
                        .uuid(UUID.randomUUID())
                        .user(userMapper.userDtoToUser(userService.getUserById(id)))
                        .chat(chat)
                        .counter(0)
                        .build()))
                .collect(Collectors.toList());
//                .map(user -> UserChatCounter.builder()
//                        .uuid(UUID.randomUUID())
//                        .user(user)
//                        .chat(chat)
//                        .counter(0)
//                        .build())
//                .collect(Collectors.toList());
//        chat.setParticipants(users);

//        TODO not null
        return chatMapper.chatToChatDto(new Chat(), null, 0);
    }

    @Override
    public Chat getChatEntityById(UUID chatId) {
        return chatRepository.findById(chatId).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<UserDto> getMembersForChat(UUID chatId) {
        return chatRepository.findById(chatId).orElseThrow().getParticipants().stream()
                .map(UserChatCounter::getUser)
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public long countAllByUser(UUID userId) {
        return chatRepository.countAllByUser(userId.toString());
    }

    @Override
    @SneakyThrows
    public void handleMessageAndGetLinkedSessions(WebSocketSession session, String message) {
        ObjectMapper mapper = new ObjectMapper();
        final var root = mapper.readTree(message);
        final var chatId = UUID.fromString(root.get("chatId").textValue());
        final var senderId = UUID.fromString(root.get("senderId").textValue());
        final var linkedSessions = chatSessions.stream().filter(item -> item.getChatId().equals(chatId)).collect(Collectors.toList());
//        final var linkedChatListSessions = chatListSessions.stream().filter(null).collect(Collectors.toList());

        if (root.get("isInit") != null) {
            chatSessions.add(Session.builder().session(session).chatId(chatId).senderId(senderId).build());
            userChatCounterRepository.findAllByChatChatId(chatId).stream()
                    .filter(item -> item.getUser().getUuid().equals(senderId))
                    .forEach(item -> item.setCounter(0));
            log.info("Registered session " + session + " for chat " + chatId + " and sender " + senderId);
        } else {
            final var msg = messageService.saveMessage(root.get("body").textValue(), chatId, senderId);
            AtomicReference<List<UserChatCounter>> binders = new AtomicReference<>(userChatCounterRepository.findAllByChatChatId(chatId));

            linkedSessions.forEach(linkedSession -> {
//                chatListSessions.stream().filter(item -> item.getSenderId().equals(linkedSession.getSenderId()))
                binders.set(binders.get().stream()
                        .filter(item -> !item.getUser().getUuid().equals(linkedSession.getSenderId()))
                        .collect(Collectors.toList()));
                try {
                    linkedSession.getSession().sendMessage(new TextMessage(mapper.writeValueAsString(msg)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            binders.get().forEach(item -> {
                item.setCounter(item.getCounter() + 1);
                userChatCounterRepository.save(item);
            });

            final var chatDto = chatMapper.chatToChatDto(chatRepository.findById(chatId).orElseThrow(), messageService.getLatestByChatId(chatId), 0);

            binders.get().forEach(binder -> {
                        final var userId = binder.getUser().getUuid();
                        final var chatSessionList = chatListSessions.stream()
                                .filter(session1 -> session1.getSenderId().equals(userId))
                                .collect(Collectors.toList());
                        if (!chatSessionList.isEmpty()) {
                            chatDto.setUnreadMessages(binder.getCounter());
                            try {
                                chatSessionList.get(0).getSession().sendMessage(new TextMessage(mapper.writeValueAsString(chatDto)));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
        }

        log.info("Chat sessions: " + chatSessions.toString());
        log.info("Chat list sessions: " + chatListSessions.toString());
    }

    @Override
    @SneakyThrows
    public void handleChatListMessage(WebSocketSession session, String message) {
        ObjectMapper mapper = new ObjectMapper();
        final var root = mapper.readTree(message);
        final var userId = UUID.fromString(root.get("userId").textValue());
        chatListSessions.add(Session.builder().senderId(userId).session(session).build());
        log.info("Registered chat list session " + session);
    }

    @Override
    public void removeSession(WebSocketSession session) {
        chatSessions = chatSessions.stream().filter(item -> !item.getSession().equals(session)).collect(Collectors.toList());
    }

    @Override
    public void removeChatListSession(WebSocketSession session) {
        chatListSessions = chatListSessions.stream().filter(item -> !item.getSession().equals(session)).collect(Collectors.toList());
    }
}