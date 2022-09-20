package io.dice.chatservice.repository;

import io.dice.chatservice.entity.Chat;
import io.dice.chatservice.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends PagingAndSortingRepository<Message, UUID> {

    long countAllByChat(Chat chat);

    List<Message> findAllByChatOrderByTimestampDesc(Chat chat, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM chat " +
            "    JOIN user_chat_counter ucc ON chat.chat_id = ucc.chat_chat_id " +
            "    JOIN message m ON chat.chat_id = m.chat_chat_id " +
            "WHERE chat_id = ?1 " +
            "ORDER BY m.timestamp DESC " +
            "LIMIT 1")
    Optional<Message> findLatestByChatId(String uuid);
}
