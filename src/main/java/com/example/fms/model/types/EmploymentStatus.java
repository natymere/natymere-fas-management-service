package com.example.fms.model.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EmploymentStatus {
    EMPLOYED("employed"),
    UNEMPLOYED("unemployed");

    private final String value;

    EmploymentStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
