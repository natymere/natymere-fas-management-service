package com.example.fms.validation;

public class DataCleaner {
    // remove leading/trailing and reduces internal whitespaces
    public static String cleanString(String input) {
        if (input == null) return null;

        return input.strip().replaceAll("\\s+", " ");
    }
}
