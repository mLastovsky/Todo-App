package com.mLastovsky.validator;

import com.mLastovsky.dto.CreateTodoDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TodoValidator implements Validator<CreateTodoDto> {

    private static final TodoValidator INSTANCE = new TodoValidator();

    @Override
    public ValidationResult validate(CreateTodoDto object) {
        log.info("Starting validation for Todo: {}", object);

        ValidationResult validationResult = new ValidationResult();

        validateUserId(object, validationResult);
        validateTask(object, validationResult);

        if (!validationResult.isValid()) {
            log.warn("Validation failed for Todo: {}", object);
        } else {
            log.info("Validation successful for Todo: {}", object);
        }

        return validationResult;
    }

    private void validateUserId(CreateTodoDto object, ValidationResult validationResult) {
        if (object.getUserId() == null) {
            log.error("Invalid UserId for Todo: {}", object);
            validationResult.add(Error.of("userId.invalid", "All tasks must have a user"));
        } else {
            log.debug("UserId validation passed for Todo: {}", object);
        }
    }

    private void validateTask(CreateTodoDto object, ValidationResult validationResult) {
        if (object.getTask() == null || object.getTask().isEmpty()) {
            log.error("Invalid task for Todo: {}", object);
            validationResult.add(Error.of("task.invalid", "Task cannot be empty"));
        } else {
            log.debug("Task validation passed for Todo: {}", object);
        }
    }

    public static TodoValidator getInstance() {
        return INSTANCE;
    }
}
