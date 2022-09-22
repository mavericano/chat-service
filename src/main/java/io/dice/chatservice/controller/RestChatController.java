package io.dice.chatservice.controller;

import io.dice.chatservice.dto.ChatDto;
import io.dice.chatservice.dto.UserDto;
import io.dice.chatservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin()
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
@Slf4j
public class RestChatController {

    private final ChatService chatService;

    @GetMapping(path = "/{chatId}/members")
    public List<UserDto> getMembersForChat(@PathVariable UUID chatId) {
        return chatService.getMembersForChat(chatId);
    }

    @GetMapping(path = "/sender/{uuid}", params = {"page", "size"})
    public ResponseEntity<List<ChatDto>> getAllChatsPaginatedForUser(@PathVariable UUID uuid,
                                                                    @RequestParam int page,
                                                                    @RequestParam int size) {
        log.info("page: " + page);
        log.info("size: " + size);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("x-total-count", String.valueOf(chatService.countAllByUser(uuid)));
        responseHeaders.setAccessControlExposeHeaders(List.of("x-total-count"));
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(chatService.getAllChatsPaginatedForUser(uuid, page, size));
    }

    //need participants
    @PostMapping
    public ChatDto createChat(@RequestBody HashMap<String, Object> params) {
        final var participants = (List<String>) params.get("participants");
        final var chatId = (String)params.get("chatId");
        final var chatName = (String)params.get("chatName");
        return chatService.createChat(participants, chatId, chatName);
    }

    @PutMapping("/{id}")
    public ChatDto modifyChat(@PathVariable UUID id,
                              @RequestBody List<String> participants) {
        return chatService.modifyChat(participants, id);
    }
}
