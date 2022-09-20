package io.dice.chatservice.service;

import io.dice.chatservice.dto.UserDto;

import java.util.UUID;

public interface UserService {
    UserDto getUserById(UUID uuid);


}
