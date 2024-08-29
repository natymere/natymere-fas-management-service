package com.example.fms.model.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum HouseholdRelationship {
    FATHER("father"),
    MOTHER("mother"),
    DAUGHTER("daughter"),
    SON("son"),
    WIFE("wife"),
    HUSBAND("husband");

    private final String value;

    HouseholdRelationship(String value) {
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
