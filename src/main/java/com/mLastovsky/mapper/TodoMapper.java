package com.mLastovsky.mapper;

import com.mLastovsky.dto.TodoDto;
import com.mLastovsky.entity.TodoEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TodoMapper implements Mapper<TodoEntity, TodoDto> {

    private static final TodoMapper INSTANCE = new TodoMapper();

    @Override
    public TodoDto mapFrom(TodoEntity object) {
        log.info("Mapping TodoEntity to TodoDto with id: {}", object.getId());

        return TodoDto.builder()
                .id(object.getId())
                .userId(object.getUserId())
                .task(object.getTask())
                .completed(object.getCompleted())
                .build();
    }

    public static TodoMapper getInstance() {
        return INSTANCE;
    }
}
