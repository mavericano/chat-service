package io.dice.chatservice.repository;

import io.dice.chatservice.entity.UserChatCounter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserChatCounterRepository extends JpaRepository<UserChatCounter, UUID> {
    Optional<UserChatCounter> findByUserUuid(UUID userUuid);
    List<UserChatCounter> findAllByChatChatId(UUID chatId);
    Optional<UserChatCounter> findByChatChatIdAndUserUuid(UUID chatId, UUID userId);
}