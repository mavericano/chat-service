package io.dice.chatservice.mapper;

import io.dice.chatservice.entity.Chat;
import io.dice.chatservice.entity.User;
import io.dice.chatservice.entity.UserChatCounter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class BindingMapper {

    public UserChatCounter userToCounter(List<UUID> participants, Chat chat) {
        return null;
    }

}
