package com.example.ApiService.user.mapper;

import com.example.ApiService.user.dtos.UserDto;
import com.example.ApiService.user.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {
    public UserDto toUserDto(UserEntity user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        return dto;
    }

    public List<UserDto> toUserDtos(Collection<UserEntity> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }
        return entities.stream().map(this::toUserDto).toList();
    }
}
