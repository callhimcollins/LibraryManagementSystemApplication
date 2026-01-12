package com.project.LibraryManagementSystemApplication.mapper;


import com.project.LibraryManagementSystemApplication.model.User;
import com.project.LibraryManagementSystemApplication.payload.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        userDto.setPhone(user.getPhone());
        userDto.setLastLogin(user.getLastLogin());
        userDto.setRole(user.getRole());

        return userDto;
    }

    public static List<UserDto> toDtoList(List<User> users) {
        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Set<UserDto> toDtoSet(Set<User> users) {
        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toSet());
    }

    public static User toEntity(UserDto dto){
        User createdUser = new User();

        createdUser.setEmail(dto.getEmail());
        createdUser.setPassword(dto.getPassword());
        createdUser.setFullName(dto.getFullName());
        createdUser.setPhone(dto.getPhone());
        createdUser.setCreatedAt(LocalDateTime.now());
        createdUser.setRole(dto.getRole());

        return createdUser;
    }
}
