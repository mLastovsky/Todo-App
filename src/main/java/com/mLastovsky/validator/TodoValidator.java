package com.mLastovsky.validator;

import com.mLastovsky.dto.TodoDto;

public class TodoValidator implements Validator<TodoDto>{

    private static final TodoValidator INSTANCE = new TodoValidator();

    @Override
    public ValidationResult isValid(TodoDto object) {
        ValidationResult validationResult = new ValidationResult();

        if(object.getUserId() == null){
            validationResult.add(Error.of("userId.invalid", "All tasks must have users"));
        }

        return validationResult;
    }

    public static TodoValidator getInstance(){
        return INSTANCE;
    }
}
