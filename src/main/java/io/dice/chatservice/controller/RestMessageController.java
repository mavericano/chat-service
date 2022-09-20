package io.dice.chatservice.controller;

import io.dice.chatservice.dto.ChatDto;
import io.dice.chatservice.dto.MessageDto;
import io.dice.chatservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class RestMessageController {

    private final MessageService messageService;

    @GetMapping(path = "/chat/{uuid}", params = {"page", "size"})
    public ResponseEntity<List<MessageDto>> getAllMessagesPaginatedForChat(
            @PathVariable UUID uuid,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        log.info(String.format("page: %d", page));
        log.info(String.format("size: %d", size));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("x-total-count", String.valueOf(messageService.countAllByChat(uuid)));
        responseHeaders.setAccessControlExposeHeaders(List.of("x-total-count"));
        final var response = messageService.getAllMessagesPaginated(uuid, page, size);
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(response);
    }
}
