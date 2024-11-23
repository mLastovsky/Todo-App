package com.mLastovsky.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TodoEntity extends Entity{

    private Long userId;
    private String task;
    private Boolean completed;
}
