package io.dice.chatservice.repository;

import io.dice.chatservice.entity.Chat;
import io.dice.chatservice.entity.Message;
import io.dice.chatservice.entity.UserChatCounter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ChatRepository extends PagingAndSortingRepository<Chat, UUID> {

    @Query(nativeQuery = true,
            value = "SELECT chat.*, " +
                    "  (SELECT MAX(timestamp) " +
                    "   FROM message " +
                    "   WHERE message.chat_chat_id = chat.chat_id) as max_timestamp " +
                    "   FROM chat " +
                    "JOIN user_chat_counter ucc ON chat.chat_id = ucc.chat_chat_id " +
                    "LEFT JOIN message ON chat.chat_id = message.chat_chat_id " +
                    "WHERE user_uuid = ?1 " +
                    "GROUP BY chat_id " +
                    "ORDER BY max_timestamp DESC " +
                    "LIMIT ?3 " +
                    "OFFSET ?2")
    List<Chat> findAllPaginatedByUserId(String userId, int offset, int size);

    @Query(nativeQuery = true,
            value = "SELECT count(*) FROM chat " +
                    "JOIN user_chat_counter ucc on chat.chat_id = ucc.chat_chat_id " +
                    "WHERE user_uuid = ?1 ")
    long countAllByUser(String userId);
}
