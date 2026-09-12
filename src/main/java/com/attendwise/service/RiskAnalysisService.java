package com.attendwise.service;

import com.attendwise.model.RiskLevel;

public class RiskAnalysisService {
    private final AttendanceService attendanceService;

    public RiskAnalysisService(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    public RiskLevel analyse(String studentId) {
        double percentage = attendanceService.getAttendancePercentage(studentId);
        if (percentage < 65) return RiskLevel.CRITICAL;
        if (percentage < 75) return RiskLevel.WATCH;
        return RiskLevel.SAFE;
    }

    public String message(String studentId) {
        double percentage = attendanceService.getAttendancePercentage(studentId);
        RiskLevel level = analyse(studentId);
        return String.format("Attendance %.2f%% -> %s", percentage, level);
    }
}
