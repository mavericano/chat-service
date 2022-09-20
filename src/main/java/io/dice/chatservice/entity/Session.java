package io.dice.chatservice.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

@Data
@Builder
public class Session {

    private UUID chatId;
    private UUID senderId;
    private WebSocketSession session;

}
