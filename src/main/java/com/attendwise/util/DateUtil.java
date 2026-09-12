package com.attendwise.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private DateUtil() {}

    public static LocalDate parse(String value) {
        try {
            return LocalDate.parse(value, FORMATTER);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Date must use YYYY-MM-DD format.");
        }
    }
}
