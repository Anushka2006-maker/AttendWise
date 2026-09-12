package com.attendwise;

import com.attendwise.service.*;
import com.attendwise.storage.FileManager;
import com.attendwise.util.InputValidator;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final StudentService studentService;
    private final AttendanceService attendanceService;
    private final RiskAnalysisService riskService;
    private final ReportService reportService;

    public Main() {
        FileManager fileManager = new FileManager("data");
        studentService = new StudentService(fileManager);
        attendanceService = new AttendanceService(studentService, fileManager);
        riskService = new RiskAnalysisService(attendanceService);
        reportService = new ReportService(studentService, attendanceService, riskService);
    }

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        System.out.println("\n=== AttendWise ===");
        System.out.println("Smart Attendance and Academic Alert System");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> addStudent();
                    case "2" -> listStudents();
                    case "3" -> searchStudent();
                    case "4" -> removeStudent();
                    case "5" -> markAttendance();
                    case "6" -> showAttendance();
                    case "7" -> showRisk();
                    case "8" -> generateReport();
                    case "0" -> running = false;
                    default -> System.out.println("Please choose a valid menu option.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Input error: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Operation failed: " + ex.getMessage());
            }
        }
        System.out.println("Goodbye.");
    }

    private void printMenu() {
        System.out.println("\n1. Add student");
        System.out.println("2. List students");
        System.out.println("3. Search student");
        System.out.println("4. Remove student");
        System.out.println("5. Mark attendance");
        System.out.println("6. View attendance");
        System.out.println("7. Analyse attendance risk");
        System.out.println("8. Generate report");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    private void addStudent() {
        System.out.print("Student ID: ");
        String id = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Course: ");
        String course = scanner.nextLine();
        System.out.print("Semester: ");
        int semester = InputValidator.positiveInt(scanner.nextLine(), "Semester");

        studentService.addStudent(id, name, course, semester);
        System.out.println("Student added successfully.");
    }

    private void listStudents() {
        studentService.getAllStudents().forEach(System.out::println);
    }

    private void searchStudent() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        studentService.findById(id).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Student not found.")
        );
    }

    private void removeStudent() {
        System.out.print("Enter ID to remove: ");
        String id = scanner.nextLine();
        System.out.println(studentService.removeStudent(id)
                ? "Student removed."
                : "Student not found.");
    }

    private void markAttendance() {
        System.out.print("Student ID: ");
        String id = scanner.nextLine();
        System.out.print("Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.print("Present? (y/n): ");
        boolean present = scanner.nextLine().trim().equalsIgnoreCase("y");

        attendanceService.markAttendance(id, date, present);
        System.out.println("Attendance recorded.");
    }

    private void showAttendance() {
        System.out.print("Student ID: ");
        String id = scanner.nextLine();
        System.out.printf("Attendance: %.2f%%%n",
                attendanceService.getAttendancePercentage(id));
        attendanceService.getRecords(id).forEach(System.out::println);
    }

    private void showRisk() {
        System.out.print("Student ID: ");
        String id = scanner.nextLine();
        System.out.println(riskService.analyse(id));
    }

    private void generateReport() {
        String path = reportService.generateReport();
        System.out.println("Report written to: " + path);
    }
}
