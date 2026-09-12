package com.attendwise.util;

public final class InputValidator {
    private InputValidator() {}

    public static int positiveInt(String value, String field) {
        try {
            int number = Integer.parseInt(value.trim());
            if (number <= 0) throw new NumberFormatException();
            return number;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(field + " must be a positive integer.");
        }
    }
}
