package com.mLastovsky.validator;

import com.mLastovsky.dto.UserDto;

public class UserValidator implements Validator<UserDto> {

    private static final UserValidator INSTANCE = new UserValidator();

    @Override
    public ValidationResult isValid(UserDto object) {
        var validationResult = new ValidationResult();

        //TODO: should realise correct validation of user

        return validationResult;
    }

    public static UserValidator getInstance(){
        return INSTANCE;
    }
}
