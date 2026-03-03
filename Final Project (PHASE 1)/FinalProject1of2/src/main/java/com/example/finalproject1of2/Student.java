package com.example.finalproject1of2;

public class Student extends User {
    private int studentId;

    public Student(String userId, String name, String email, int studentId) {
        super(userId, name, email);
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public String getUserType() {
        return "Student";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Student ID: %d", studentId);
    }
}