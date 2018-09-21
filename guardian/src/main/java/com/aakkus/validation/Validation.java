package com.aakkus.validation;

import com.aakkus.validation.exception.GuardianValidationException;

public interface Validation<T> {

    void validate(T t) throws GuardianValidationException;
}