package com.mLastovsky.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {

    Long id;
    String username;
    String email;
    String password;
}
