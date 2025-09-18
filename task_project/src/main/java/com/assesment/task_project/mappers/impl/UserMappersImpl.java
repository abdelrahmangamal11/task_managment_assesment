package com.assesment.task_project.mappers.impl;

import com.assesment.task_project.domain.Dto.UserDto;
import com.assesment.task_project.domain.entities.User;
import com.assesment.task_project.mappers.UserMappers;
import org.springframework.stereotype.Component;

@Component
public class UserMappersImpl implements UserMappers {
    @Override
    public UserDto fromUserToDto(User user) {
        return new UserDto(
                user.getEmail(),
                user.getPassword(),
                user.getName()
        );
    }

    @Override
    public User fromDtoToUser(UserDto userDto) {
        return new User(
                null,
                userDto.email(),
                userDto.password(),
                userDto.name(),
                null
        );
    }
}
