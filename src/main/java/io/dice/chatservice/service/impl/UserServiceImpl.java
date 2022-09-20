package io.dice.chatservice.service.impl;

import io.dice.chatservice.dto.UserDto;
import io.dice.chatservice.entity.User;
import io.dice.chatservice.mapper.UserMapper;
import io.dice.chatservice.repository.UserClient;
import io.dice.chatservice.repository.UserRepository;
import io.dice.chatservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserClient userClient;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getUserById(UUID uuid) {
        final var user = userRepository.findById(uuid);
        return userMapper.userToUserDto(user.orElseGet(() -> {
            User unknownUser = userClient.getUserById(uuid);
            userRepository.save(unknownUser);
            return unknownUser;
        }));
//        if (user.isPresent()) {
//            return userMapper.userToUserDto(user.get());
//        } else {
//            User unknownUser = userClient.getUserById(uuid);
//            userRepository.save(unknownUser);
//            return userMapper.userToUserDto(unknownUser);
//        }
    }
}
