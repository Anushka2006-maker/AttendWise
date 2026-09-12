package com.attendwise.service;

import com.attendwise.model.Student;
import com.attendwise.storage.FileManager;

import java.util.*;

public class StudentService {
    private final Map<String, Student> students = new LinkedHashMap<>();
    private final FileManager fileManager;

    public StudentService(FileManager fileManager) {
        this.fileManager = fileManager;
        load();
    }

    public void addStudent(String id, String name, String course, int semester) {
        if (students.containsKey(id.trim())) throw new IllegalArgumentException("Student ID already exists.");
        students.put(id.trim(), new Student(id, name, course, semester));
        save();
    }

    public Optional<Student> findById(String id) {
        return Optional.ofNullable(students.get(id.trim()));
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    public boolean removeStudent(String id) {
        Student removed = students.remove(id.trim());
        if (removed != null) {
            save();
            return true;
        }
        return false;
    }

    private void load() {
        for (String line : fileManager.read("students.txt")) {
            if (line.isBlank()) continue;
            String[] p = line.split("\\|", -1);
            if (p.length == 4) {
                try {
                    students.put(p[0], new Student(p[0], p[1], p[2], Integer.parseInt(p[3])));
                } catch (RuntimeException ignored) {
                    // Ignore malformed old records instead of stopping application startup.
                }
            }
        }
    }

    private void save() {
        List<String> lines = students.values().stream()
                .map(s -> String.join("|", s.getId(), s.getName(), s.getCourse(),
                        String.valueOf(s.getSemester())))
                .toList();
        fileManager.write("students.txt", lines);
    }
}
