package com.example.fms.model.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MaritalStatus {
    SINGLE("Single"),
    MARRIED("Married"),
    WIDOWED("Windowed"),
    DIVORCED("Divorced");

    private final String value;

    MaritalStatus(String value) {
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
