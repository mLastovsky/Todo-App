package com.mLastovsky.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TodoDto {

    Long userId;
    String task;
    Boolean completed;
}
