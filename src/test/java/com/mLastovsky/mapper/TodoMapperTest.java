package com.mLastovsky.mapper;

import com.mLastovsky.dto.TodoDto;
import com.mLastovsky.entity.TodoEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TodoMapperTest {

    private static TodoMapper todoMapper;

    @BeforeAll
    static void prepareInstance() {
        todoMapper = TodoMapper.getInstance();
    }

    @Test
    void mapFromTest() {
        var todoDto = TodoDto.builder()
                .id(1L)
                .userId(1L)
                .task("some task")
                .completed(true)
                .build();

        var todoEntity = TodoEntity.builder()
                .id(1L)
                .userId(1L)
                .task("some task")
                .completed(true)
                .build();

        assertEquals(todoDto, todoMapper.mapFrom(todoEntity));
    }
}
