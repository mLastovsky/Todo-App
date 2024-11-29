package com.mLastovsky.mapper;

import com.mLastovsky.dto.CreateTodoDto;
import com.mLastovsky.entity.TodoEntity;

public class CreateTodoDtoMapper implements Mapper<CreateTodoDto, TodoEntity> {

    private static final CreateTodoDtoMapper INSTANCE = new CreateTodoDtoMapper();

    @Override
    public TodoEntity mapFrom(CreateTodoDto object) {
        return TodoEntity.builder()
                .userId(object.getUserId())
                .task(object.getTask())
                .completed(object.getCompleted())
                .build();
    }

    public static CreateTodoDtoMapper getInstance() {
        return INSTANCE;
    }
}
