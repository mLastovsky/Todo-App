package com.mLastovsky.exception;

import com.mLastovsky.validator.Error;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException{

    private List<Error> errors;

    public ValidationException(List<Error> errors){
        this.errors = errors;
    }
}
