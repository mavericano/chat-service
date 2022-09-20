package io.dice.chatservice.repository.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dice.chatservice.dto.UserDto;
import io.dice.chatservice.entity.User;
import io.dice.chatservice.repository.UserClient;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserClientImpl implements UserClient {

    @Value("${coreDataUsersUrl}")
    private String URL;

    private final RestTemplate restTemplate;

    @Override
    @SneakyThrows
    public User getUserById(UUID uuid) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        try {
            final var response= restTemplate.exchange(URL + "/" + uuid.toString(), HttpMethod.GET, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(response.getBody());
                String stringId = node.get("ID").textValue();
                String nickname = node.get("username").textValue();
                System.out.println(stringId);
                System.out.println(nickname);
                //replace with restTemplate
                return User.builder()
                        .uuid(UUID.fromString(stringId))
                        .nickname(nickname)
                        .build();
            } else {
                log.warn(response.getStatusCode().toString());
                throw new RuntimeException("not 2xx response code, idk what to do");
            }
        } catch (HttpClientErrorException.NotFound e) {
            //TODO add exception handler later
            throw new RuntimeException("no such user in remote");
        }
    }
}
