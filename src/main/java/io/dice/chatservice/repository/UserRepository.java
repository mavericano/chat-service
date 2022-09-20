package io.dice.chatservice.repository;

import io.dice.chatservice.dto.UserDto;
import io.dice.chatservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    UserDto getUserByUuid(UUID uuid);
}
