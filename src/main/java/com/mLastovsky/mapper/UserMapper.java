package com.mLastovsky.mapper;

import com.mLastovsky.dto.CreateUserDto;
import com.mLastovsky.dto.UserDto;
import com.mLastovsky.entity.UserEntity;

public class UserMapper implements Mapper<UserEntity, UserDto> {

    private static final UserMapper INSTANCE = new UserMapper();

    @Override
    public UserDto mapFrom(UserEntity object) {
        return UserDto.builder()
                .id(object.getId())
                .username(object.getUsername())
                .email(object.getEmail())
                .password(object.getPassword())
                .build();
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}
