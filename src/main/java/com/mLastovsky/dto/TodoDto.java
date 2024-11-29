package com.mLastovsky.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TodoDto {

    Long id;
    Long userId;
    String task;
    Boolean completed;
}
