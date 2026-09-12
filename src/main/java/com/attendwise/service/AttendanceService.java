package com.attendwise.service;

import com.attendwise.model.AttendanceRecord;
import com.attendwise.storage.FileManager;

import java.time.LocalDate;
import java.util.*;

public class AttendanceService {
    private final StudentService studentService;
    private final FileManager fileManager;
    private final List<AttendanceRecord> records = new ArrayList<>();

    public AttendanceService(StudentService studentService, FileManager fileManager) {
        this.studentService = studentService;
        this.fileManager = fileManager;
        load();
    }

    public void markAttendance(String studentId, LocalDate date, boolean present) {
        if (studentService.findById(studentId).isEmpty())
            throw new IllegalArgumentException("Student does not exist.");

        boolean duplicate = records.stream()
                .anyMatch(r -> r.studentId().equals(studentId.trim()) && r.date().equals(date));
        if (duplicate) throw new IllegalArgumentException("Attendance already exists for this date.");

        records.add(new AttendanceRecord(studentId.trim(), date, present));
        save();
    }

    public List<AttendanceRecord> getRecords(String studentId) {
        return records.stream()
                .filter(r -> r.studentId().equals(studentId.trim()))
                .sorted(Comparator.comparing(AttendanceRecord::date))
                .toList();
    }

    public double getAttendancePercentage(String studentId) {
        List<AttendanceRecord> studentRecords = getRecords(studentId);
        if (studentRecords.isEmpty()) return 0.0;
        long present = studentRecords.stream().filter(AttendanceRecord::present).count();
        return present * 100.0 / studentRecords.size();
    }

    private void load() {
        for (String line : fileManager.read("attendance.txt")) {
            if (line.isBlank()) continue;
            String[] p = line.split("\\|", -1);
            if (p.length == 3) {
                try {
                    records.add(new AttendanceRecord(p[0], LocalDate.parse(p[1]),
                            Boolean.parseBoolean(p[2])));
                } catch (RuntimeException ignored) {
                    // Ignore malformed records.
                }
            }
        }
    }

    private void save() {
        List<String> lines = records.stream()
                .map(r -> String.join("|", r.studentId(), r.date().toString(),
                        String.valueOf(r.present())))
                .toList();
        fileManager.write("attendance.txt", lines);
    }
}
