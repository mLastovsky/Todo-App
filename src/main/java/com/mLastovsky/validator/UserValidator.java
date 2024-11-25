package com.mLastovsky.validator;

import com.mLastovsky.dto.UserDto;

import java.util.regex.Pattern;

public class UserValidator implements Validator<UserDto> {

    private static final UserValidator INSTANCE = new UserValidator();

    @Override
    public ValidationResult isValid(UserDto object) {
        var validationResult = new ValidationResult();

        if(object.getUsername().length()<2){
            validationResult.add(Error.of("invalid.username", "Username should consist at least 2 symbols"));
        }

        if(!isCorrectEmail(object)){
            validationResult.add(Error.of("invalid.email", "Email is incorrect"));
        }

        return validationResult;
    }

    private static boolean isCorrectEmail(UserDto object) {
        final var EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$";

        return Pattern.matches(EMAIL_REGEX,object.getEmail());
    }


    public static UserValidator getInstance(){
        return INSTANCE;
    }
}
