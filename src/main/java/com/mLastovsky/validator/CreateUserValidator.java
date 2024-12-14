package com.mLastovsky.validator;

import com.mLastovsky.dto.CreateUserDto;

import java.util.regex.Pattern;

public class CreateUserValidator implements Validator<CreateUserDto> {

    private static final CreateUserValidator INSTANCE = new CreateUserValidator();
    private static final int MIN_USERNAME_LENGTH = 2;

    @Override
    public ValidationResult validate(CreateUserDto createUserDto) {
        var validationResult = new ValidationResult();

        if (createUserDto.getUsername() == null || createUserDto.getUsername().length() < MIN_USERNAME_LENGTH) {
            validationResult.add(Error.of("invalid.username", "Username should consist at least " + MIN_USERNAME_LENGTH + " symbols"));
        }

        if (!isCorrectEmail(createUserDto)) {
            validationResult.add(Error.of("invalid.email", "Email is incorrect"));
        }

        return validationResult;
    }

    private static boolean isCorrectEmail(CreateUserDto object) {
        final var EMAIL_REGEX = "^[a-zA-z]\\w*@\\w{3,}\\.\\w{2,}$";
        return Pattern.matches(EMAIL_REGEX, object.getEmail());
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
