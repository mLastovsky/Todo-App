package com.mLastovsky.validator;

import com.mLastovsky.dto.CreateUserDto;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@Slf4j
public class CreateUserValidator implements Validator<CreateUserDto> {

    private static final CreateUserValidator INSTANCE = new CreateUserValidator();
    private static final int MIN_USERNAME_LENGTH = 2;
    private static final String EMAIL_REGEX = "^[a-zA-Z]\\w*@\\w{3,}\\.\\w{2,}$";

    @Override
    public ValidationResult validate(CreateUserDto createUserDto) {
        var validationResult = new ValidationResult();

        log.info("Starting validation for user: {}", createUserDto.getUsername());

        validateUsername(createUserDto, validationResult);
        validateEmail(createUserDto, validationResult);

        if (!validationResult.isValid()) {
            log.warn("Validation failed for user: {}", createUserDto.getUsername());
        } else {
            log.info("Validation successful for user: {}", createUserDto.getUsername());
        }

        return validationResult;
    }

    private void validateUsername(CreateUserDto createUserDto, ValidationResult validationResult) {
        if (createUserDto.getUsername() == null || createUserDto.getUsername().length() < MIN_USERNAME_LENGTH) {
            log.error("Invalid username: {}", createUserDto.getUsername());
            validationResult.add(Error.of("invalid.username", "Username should consist of at least " + MIN_USERNAME_LENGTH + " characters"));
        } else {
            log.debug("Username validation passed: {}", createUserDto.getUsername());
        }
    }

    private void validateEmail(CreateUserDto createUserDto, ValidationResult validationResult) {
        if (!isCorrectEmail(createUserDto.getEmail())) {
            log.error("Invalid email: {}", createUserDto.getEmail());
            validationResult.add(Error.of("invalid.email", "Email is incorrect"));
        } else {
            log.debug("Email validation passed: {}", createUserDto.getEmail());
        }
    }

    private boolean isCorrectEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
