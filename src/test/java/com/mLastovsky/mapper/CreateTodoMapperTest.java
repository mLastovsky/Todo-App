package com.mLastovsky.mapper;

import com.mLastovsky.dto.CreateTodoDto;
import com.mLastovsky.entity.TodoEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateTodoMapperTest {

    private static CreateTodoMapper createTodoMapper;

    @BeforeAll
    static void prepareInstance() {
        createTodoMapper = CreateTodoMapper.getInstance();
    }

    @Test
    void mapFromTest() {
        var createTodoDto = CreateTodoDto.builder()
                .userId(1L)
                .task("gym")
                .build();

        var todoEntity = TodoEntity.builder()
                .userId(1L)
                .task("gym")
                .build();

        assertEquals(todoEntity, createTodoMapper.mapFrom(createTodoDto));
    }
}
