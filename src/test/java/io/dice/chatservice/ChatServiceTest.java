package io.dice.chatservice;

import io.dice.chatservice.dto.ChatDto;
import io.dice.chatservice.entity.Chat;
import io.dice.chatservice.entity.User;
import io.dice.chatservice.entity.UserChatCounter;
import io.dice.chatservice.repository.ChatRepository;
import io.dice.chatservice.repository.UserClient;
import io.dice.chatservice.repository.UserRepository;
import io.dice.chatservice.service.impl.ChatServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private ChatServiceImpl chatService;

//    @Test
//    public void shouldCreateChat() {
//        UUID id1 = UUID.randomUUID();
//        UUID id2 = UUID.randomUUID();
//        UUID idChat = UUID.randomUUID();
//        User user1 = User.builder().uuid(id1).nickname("from_db").build();
//        User user2 = User.builder().uuid(id2).nickname("from_client").build();
//        Chat chat = Chat.builder().chatId(idChat).messages(List.of()).participants(null).build();
//
//        System.out.println(id1);
//        System.out.println(id2);
//
//        final var users = Stream.of(user1, user2).map(user -> UserChatCounter.builder()
//                        .uuid(UUID.randomUUID())
//                        .user(user)
//                        .chat(chat)
//                        .counter(0)
//                        .build())
//                .collect(Collectors.toList());
//        chat.setParticipants(users);
//
//        when(userRepository.findById(id1)).thenReturn(Optional.of(user1));
//        when(userRepository.findById(id2)).thenReturn(Optional.empty());
//
//        when(userClient.getUserById(id2)).thenReturn(user2);
//
//        when(chatRepository.save(chat)).thenReturn(chat);
//
//        Assertions.assertEquals(ChatDto.builder().build(), chatService.createChat(List.of(id1, id2), idChat));
//
//    }
}
