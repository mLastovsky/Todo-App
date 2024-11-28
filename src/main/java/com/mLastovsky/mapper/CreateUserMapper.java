package com.mLastovsky.mapper;

import com.mLastovsky.dto.CreateUserDto;
import com.mLastovsky.entity.UserEntity;

public class CreateUserMapper implements Mapper<CreateUserDto, UserEntity> {

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public UserEntity mapFrom(CreateUserDto object) {
        return UserEntity.builder()
                .username(object.getUsername())
                .email(object.getEmail())
                .password(object.getPassword())
                .build();
    }

    public static CreateUserMapper getInstance(){
        return INSTANCE;
    }
}
