package com.mLastovsky.mapper;

import com.mLastovsky.dto.CreateUserDto;
import com.mLastovsky.entity.UserEntity;

public class CreateUserMapper implements Mapper<UserEntity, CreateUserDto> {

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public CreateUserDto mapFrom(UserEntity object) {
        return CreateUserDto.builder()
                .username(object.getUsername())
                .email(object.getEmail())
                .password(object.getPassword())
                .build();
    }

    public static CreateUserMapper getInstance(){
        return INSTANCE;
    }
}
