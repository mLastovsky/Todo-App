package com.mLastovsky.validator;

import com.mLastovsky.dto.CreateUserDto;

import java.util.regex.Pattern;

public class UserValidator implements Validator<CreateUserDto> {

    private static final UserValidator INSTANCE = new UserValidator();

    @Override
    public ValidationResult isValid(CreateUserDto object) {
        var validationResult = new ValidationResult();

        if(object.getUsername().length()<2){
            validationResult.add(Error.of("invalid.username", "Username should consist at least 2 symbols"));
        }

        if(!isCorrectEmail(object)){
            validationResult.add(Error.of("invalid.email", "Email is incorrect"));
        }

        return validationResult;
    }

    private static boolean isCorrectEmail(CreateUserDto object) {
        final var EMAIL_REGEX = "^[a-zA-z]\\w*@\\w{3,}\\.\\w{2,}$";

        return Pattern.matches(EMAIL_REGEX,object.getEmail());
    }


    public static UserValidator getInstance(){
        return INSTANCE;
    }
}
