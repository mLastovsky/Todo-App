package com.mLastovsky.mapper;

import com.mLastovsky.dto.CreateUserDto;
import com.mLastovsky.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateUserMapper implements Mapper<CreateUserDto, UserEntity> {

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public UserEntity mapFrom(CreateUserDto object) {
        log.info("Mapping CreateUserDto to UserEntity with username: {}", object.getUsername());

        return UserEntity.builder()
                .username(object.getUsername())
                .email(object.getEmail())
                .password(object.getPassword())
                .build();
    }

    @Override
    public CreateUserDto mapTo(UserEntity object) {
        log.info("Mapping UserEntity to CreateUserDto with username: {}", object.getUsername());

        return CreateUserDto.builder()
                .username(object.getUsername())
                .email(object.getEmail())
                .password(object.getPassword())
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
