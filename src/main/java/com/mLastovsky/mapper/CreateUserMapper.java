package com.mLastovsky.mapper;

import com.mLastovsky.dto.UserDto;
import com.mLastovsky.entity.UserEntity;

public class UserDtoMapper implements Mapper<UserEntity, UserDto> {

    private static final UserDtoMapper INSTANCE = new UserDtoMapper();

    @Override
    public UserDto mapFrom(UserEntity object) {
        return UserDto.builder()
                .username(object.getUsername())
                .email(object.getEmail())
                .password(object.getPassword())
                .build();
    }

    public static UserDtoMapper getInstance(){
        return INSTANCE;
    }
}
