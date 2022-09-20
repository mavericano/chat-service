package io.dice.chatservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dice.chatservice.dto.MessageDto;
import io.dice.chatservice.dto.UserDto;
import io.dice.chatservice.entity.Chat;
import io.dice.chatservice.entity.Message;
import io.dice.chatservice.entity.User;
import io.dice.chatservice.entity.UserChatCounter;
import io.dice.chatservice.mapper.ChatMapper;
import io.dice.chatservice.mapper.MessageMapper;
import io.dice.chatservice.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.awt.print.Pageable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootApplication
public class ChatServiceApplication {

    @Bean
    CommandLineRunner run(MessageMapper messageMapper, ChatMapper chatMapper, MessageRepository messageRepository, ChatRepository chatRepository, UserRepository userRepository, UserChatCounterRepository userChatCounterRepository, UserClient userClient) {
        return args -> {
//            ObjectMapper mapper = new ObjectMapper();
//            System.out.println(mapper.writeValueAsString(MessageDto.builder()
//                            .uuid(UUID.randomUUID())
//                            .sender(UserDto.builder().uuid(UUID.randomUUID()).nickname("kunya").build())
//                            .text("hello")
//                            .timestamp(Timestamp.from(Instant.now()))
//                    .build()));
//            System.out.println(chatRepository.findByParticipants());
//            System.out.println(messageMapper.messageToMessageDto(messageRepository.findTopByOrderByTimestamp(chatRepository.findById(UUID.fromString("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeee4")).get()), null));
//            System.out.println(chatRepository.findAllByParticipantsContaining(UUID.fromString("027b4656-bccc-4a0a-9508-6a9ae173bb95").toString(), 0, 100).stream().map(i -> chatMapper.chatToChatDto(i, null)).collect(Collectors.toList()));
//            userClient.getUserById(UUID.fromString("027b4656-bccc-4a0a-9508-6a9ae173bb95"));
//            final var user = User.builder()
//                    .uuid(UUID.randomUUID())
//                    .nickname("dr_grave")
//                    .build();
//            final var chat = Chat.builder()
//                    .chatId(UUID.randomUUID())
//                    .build();
//            final var message1 = Message.builder()
//                    .chat(chat)
//                    .sender(user)
//                    .text("Goodbye, my love!")
//                    .timestamp(Timestamp.from(Instant.now()))
//                    .build();
//            final var message2 = Message.builder()
//                    .chat(chat)
//                    .sender(user)
//                    .text("Sorry, не могу поставить")
//                    .timestamp(Timestamp.from(Instant.now()))
//                    .build();
//            final var user_chat = UserChatCounter.builder()
//                    .uuid(UUID.randomUUID())
//                    .user(user)
//                    .chat(chat)
//                    .counter(50)
//                    .build();
//            chat.setMessages(List.of(message1, message2));
//            userRepository.save(user);
//            chatRepository.save(chat);
//            messageRepository.save(message1);
//            messageRepository.save(message2);
//            userChatCounterRepository.save(user_chat);
//            chatRepository.save(new Chat(UUID.fromString("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee"), UUID.fromString("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee").toString()));
//            messageRepository.save(new Message(UUID.randomUUID(), null, Timestamp.from(Instant.now()), "hello world", UUID.randomUUID()));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ChatServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addExposedHeader(HttpHeaders.AUTHORIZATION);
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
