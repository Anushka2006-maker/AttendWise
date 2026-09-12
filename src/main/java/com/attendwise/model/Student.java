package com.attendwise.model;

public class Student {
    private final String id;
    private String name;
    private String course;
    private int semester;

    public Student(String id, String name, String course, int semester) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Student ID cannot be empty.");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Student name cannot be empty.");
        if (course == null || course.isBlank()) throw new IllegalArgumentException("Course cannot be empty.");
        if (semester <= 0) throw new IllegalArgumentException("Semester must be positive.");
        this.id = id.trim();
        this.name = name.trim();
        this.course = course.trim();
        this.semester = semester;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCourse() { return course; }
    public int getSemester() { return semester; }

    public void update(String name, String course, int semester) {
        if (name == null || name.isBlank() || course == null || course.isBlank() || semester <= 0)
            throw new IllegalArgumentException("Invalid student details.");
        this.name = name.trim();
        this.course = course.trim();
        this.semester = semester;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + course + " | Semester " + semester;
    }
}
