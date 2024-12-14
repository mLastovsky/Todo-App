package com.mLastovsky.mapper;

import com.mLastovsky.dto.TodoDto;
import com.mLastovsky.entity.TodoEntity;

public class TodoMapper implements Mapper<TodoEntity, TodoDto> {

    private static final TodoMapper INSTANCE = new TodoMapper();

    @Override
    public TodoDto mapFrom(TodoEntity object) {
        return TodoDto.builder()
                .id(object.getId())
                .userId(object.getUserId())
                .task(object.getTask())
                .completed(object.getCompleted())
                .build();
    }

    public static TodoMapper getInstance(){
        return INSTANCE;
    }
}
