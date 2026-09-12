package com.attendwise.service;

import com.attendwise.model.Student;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

public class ReportService {
    private final StudentService studentService;
    private final AttendanceService attendanceService;
    private final RiskAnalysisService riskService;

    public ReportService(StudentService studentService, AttendanceService attendanceService,
                         RiskAnalysisService riskService) {
        this.studentService = studentService;
        this.attendanceService = attendanceService;
        this.riskService = riskService;
    }

    public String generateReport() {
        Path report = Paths.get("data", "attendance-report.txt");
        List<String> lines = new ArrayList<>();
        lines.add("ATTENDWISE ATTENDANCE REPORT");
        lines.add("Generated: " + LocalDateTime.now());
        lines.add("----------------------------------------");

        for (Student student : studentService.getAllStudents()) {
            lines.add(student.getId() + " | " + student.getName()
                    + " | " + String.format("%.2f%%", attendanceService.getAttendancePercentage(student.getId()))
                    + " | " + riskService.analyse(student.getId()));
        }

        try {
            Files.createDirectories(report.getParent());
            Files.write(report, lines, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            return report.toString();
        } catch (IOException e) {
            throw new IllegalStateException("Could not generate report.", e);
        }
    }
}
