package com.mLastovsky.mapper;

import com.mLastovsky.dto.CreateTodoDto;
import com.mLastovsky.entity.TodoEntity;

public class CreateTodoMapper implements Mapper<CreateTodoDto, TodoEntity> {

    private static final CreateTodoMapper INSTANCE = new CreateTodoMapper();

    @Override
    public TodoEntity mapFrom(CreateTodoDto object) {
        return TodoEntity.builder()
                .userId(object.getUserId())
                .task(object.getTask())
                .build();
    }

    public static CreateTodoMapper getInstance() {
        return INSTANCE;
    }
}
