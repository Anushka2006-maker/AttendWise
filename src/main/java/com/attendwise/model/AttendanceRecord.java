package com.attendwise.model;

import java.time.LocalDate;

public record AttendanceRecord(String studentId, LocalDate date, boolean present) {
    public AttendanceRecord {
        if (studentId == null || studentId.isBlank()) throw new IllegalArgumentException("Student ID is required.");
        if (date == null) throw new IllegalArgumentException("Date is required.");
    }

    @Override
    public String toString() {
        return date + " | " + (present ? "Present" : "Absent");
    }
}
