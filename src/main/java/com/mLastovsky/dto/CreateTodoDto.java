package com.mLastovsky.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CreateTodoDto {

    Long userId;
    String task;
}
