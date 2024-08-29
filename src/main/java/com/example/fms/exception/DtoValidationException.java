package com.example.fms.exception;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;

import java.util.Set;

@Getter
public class DtoValidationException extends RuntimeException {
    private final Set<ConstraintViolation<?>> violations;

    public DtoValidationException(Set<ConstraintViolation<?>> violations) {
        super("Dto validation failed");
        this.violations = violations;
    }

}
