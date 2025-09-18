package com.assesment.task_project.mappers;

import com.assesment.task_project.domain.Dto.UserDto;
import com.assesment.task_project.domain.entities.User;

public interface UserMappers {
    UserDto fromUserToDto(User user);
    User fromDtoToUser(UserDto userDto);
}
