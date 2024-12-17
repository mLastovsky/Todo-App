package com.mLastovsky.mapper;

import com.mLastovsky.dto.CreateTodoDto;
import com.mLastovsky.entity.TodoEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateTodoMapper implements Mapper<CreateTodoDto, TodoEntity> {

    private static final CreateTodoMapper INSTANCE = new CreateTodoMapper();

    @Override
    public TodoEntity mapFrom(CreateTodoDto object) {
        log.info("Mapping CreateTodoDto to TodoEntity with userId: {}", object.getUserId());

        return TodoEntity.builder()
                .userId(object.getUserId())
                .task(object.getTask())
                .build();
    }

    public static CreateTodoMapper getInstance() {
        return INSTANCE;
    }
}
