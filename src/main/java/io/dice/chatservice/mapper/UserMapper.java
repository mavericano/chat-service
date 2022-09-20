package io.dice.chatservice.mapper;

import io.dice.chatservice.dto.UserDto;
import io.dice.chatservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User userDtoToUser(UserDto userDto) {
        return User.builder()
                .uuid(userDto.getUuid())
                .nickname(userDto.getNickname())
                .build();
    }

    public UserDto userToUserDto(User user) {
        return UserDto.builder()
                .uuid(user.getUuid())
                .nickname(user.getNickname())
                .build();
    }
}
