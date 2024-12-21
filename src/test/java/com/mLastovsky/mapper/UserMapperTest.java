package com.mLastovsky.mapper;

import com.mLastovsky.dto.UserDto;
import com.mLastovsky.entity.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    private static UserMapper userMapper;

    @BeforeAll
    static void prepareInstance() {
        userMapper = UserMapper.getInstance();
    }

    @Test
    void mapFromTest() {
        var userEntity = UserEntity.builder()
                .id(1L)
                .username("Banderos")
                .email("Banderos@gmail.com")
                .password("123456")
                .build();

        var userDto = UserDto.builder()
                .id(1L)
                .username("Banderos")
                .email("Banderos@gmail.com")
                .password("123456")
                .build();

        assertEquals(userDto, userMapper.mapFrom(userEntity));
    }
}
