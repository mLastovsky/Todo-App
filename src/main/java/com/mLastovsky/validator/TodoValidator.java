package com.mLastovsky.validator;

import com.mLastovsky.dto.CreateTodoDto;

public class TodoValidator implements Validator<CreateTodoDto>{

    private static final TodoValidator INSTANCE = new TodoValidator();

    @Override
    public ValidationResult isValid(CreateTodoDto object) {
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
