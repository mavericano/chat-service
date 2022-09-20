package io.dice.chatservice.repository;

import io.dice.chatservice.dto.UserDto;
import io.dice.chatservice.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserClient {
    User getUserById(UUID uuid);
}
