package com.mLastovsky.mapper;

import com.mLastovsky.dto.UserDto;
import com.mLastovsky.entity.UserEntity;

public class UserMapper implements Mapper<UserDto, UserEntity> {

    private static final UserMapper INSTANCE = new UserMapper();

    @Override
    public UserEntity mapFrom(UserDto object) {
        return UserEntity.builder()
                .username(object.getUsername())
                .email(object.getPassword())
                .password(object.getPassword())
                .build();
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}
