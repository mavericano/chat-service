package io.dice.chatservice.config;

import io.dice.chatservice.controller.WSChatController;
import io.dice.chatservice.controller.WSChatListController;
import io.dice.chatservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatService chatService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WSChatController(chatService), "/chat").setAllowedOrigins("*");
        registry.addHandler(new WSChatListController(chatService), "/chats").setAllowedOrigins("*");
    }
}
