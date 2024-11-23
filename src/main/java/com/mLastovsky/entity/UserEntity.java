package com.mLastovsky.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserEntity extends Entity{

    private String username;
    private String password;
}
