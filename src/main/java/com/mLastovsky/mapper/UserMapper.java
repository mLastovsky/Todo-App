package com.mLastovsky.mapper;

import com.mLastovsky.dto.UserDto;
import com.mLastovsky.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserMapper implements Mapper<UserEntity, UserDto> {

    private static final UserMapper INSTANCE = new UserMapper();

    @Override
    public UserDto mapFrom(UserEntity object) {
        log.info("Mapping UserEntity to UserDto with username: {}", object.getUsername());

        return UserDto.builder()
                .id(object.getId())
                .username(object.getUsername())
                .email(object.getEmail())
                .password(object.getPassword())
                .build();
    }

    @Override
    public UserEntity mapTo(UserDto object) {
        log.info("Mapping UserDto to UserEntity with username: {}", object.getUsername());

        return UserEntity.builder()
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
