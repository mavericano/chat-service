package io.dice.chatservice.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
public class MessageDto {
    private UUID uuid;
    private Timestamp timestamp;
    private String text;
    private UserDto sender;
}
