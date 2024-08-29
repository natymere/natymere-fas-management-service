package com.example.fms.exception;

import com.example.fms.model.ErrorResponse;
import com.example.fms.util.RequestContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        logger.error("Resource not found exception occurred: {} Time taken: {} ms", e.getMessage(), RequestContext.getRequestDuration(), e);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found", e.getMessage(), Instant.now(), constructURI(request));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        logger.error("Illegal argument exception occurred: {} Time taken: {} ms", e.getMessage(), RequestContext.getRequestDuration(), e);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Illegal Argument", e.getMessage(), Instant.now(), constructURI(request));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(HttpMessageNotReadableException e, HttpServletRequest request) {
        logger.error("Illegal argument exception occurred: {} Time taken: {} ms", e.getMessage(), RequestContext.getRequestDuration(), e);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Illegal Argument", e.getMessage(), Instant.now(), constructURI(request));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DtoValidationException.class)
    public ResponseEntity<?> handleValidationException(DtoValidationException e, HttpServletRequest request) {

        Map<String, String> fieldErrors = new HashMap<>();
        for (ConstraintViolation<?> violation : e.getViolations()) {
            Path propertyPath = violation.getPropertyPath();
            String fieldName = propertyPath.toString();
            String err = violation.getMessage();
            fieldErrors.put(fieldName, err);
        }

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", e.getMessage(), Instant.now(), constructURI(request), fieldErrors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e, HttpServletRequest request) {
        logger.error("Unexpected error occurred: {} Time taken: {} ms", e.getMessage(), RequestContext.getRequestDuration(), e);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", "An unexpected error occurred.", Instant.now(), constructURI(request));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String constructURI(HttpServletRequest req) {
        String URI = req.getRequestURI();
        String queryString = req.getQueryString();
        return (queryString != null) ? URI + "?" + queryString : URI;
    }
}
