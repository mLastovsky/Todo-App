package com.mLastovsky.mapper;

import com.mLastovsky.dto.TodoDto;
import com.mLastovsky.entity.TodoEntity;

public class TodoMapper implements Mapper<TodoDto, TodoEntity> {

    private static final TodoMapper INSTANCE = new TodoMapper();

    @Override
    public TodoEntity mapFrom(TodoDto object) {
        return TodoEntity.builder()
                .userId(object.getUserId())
                .task(object.getTask())
                .completed(object.getCompleted())
                .build();
    }

    public static TodoMapper getInstance(){
        return INSTANCE;
    }
}
