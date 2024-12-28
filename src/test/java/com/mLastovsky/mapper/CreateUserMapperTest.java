package com.mLastovsky.mapper;

import com.mLastovsky.dto.CreateUserDto;
import com.mLastovsky.entity.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateUserMapperTest {

    private static CreateUserMapper createUserMapper;

    @BeforeAll
    static void prepareInstance() {
        createUserMapper = CreateUserMapper.getInstance();
    }

    @Test
    void mapFromTest() {
        var createUserDto = CreateUserDto.builder()
                .username("Banderos")
                .email("Banderos@gmail.com")
                .password("123456")
                .build();

        var userEntity = UserEntity.builder()
                .username("Banderos")
                .email("Banderos@gmail.com")
                .password("123456")
                .build();

        assertEquals(userEntity, createUserMapper.mapFrom(createUserDto));
    }
}
