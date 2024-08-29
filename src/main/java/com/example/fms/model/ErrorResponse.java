package com.example.fms.model;

import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Data
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private Instant timestamp;
    private String path;
    private String trace;
    private Map<String, String> fieldErrors;

    public ErrorResponse(int status, String error, String message, Instant timestamp, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
        this.path = path;
    }

    public ErrorResponse(int status, String error, String message, Instant timestamp, String path, String trace) {
        this(status, error, message, timestamp, path);
        this.trace = trace;
    }

    public ErrorResponse(int status, String error, String message, Instant timestamp, String path, Map<String, String> fieldErrors) {
        this(status, error, message, timestamp, path);
        this.fieldErrors = fieldErrors;
    }
}
