package com.example.bookingsys;

public class Staff extends User {
    private String department;

    public Staff(String userId, String name, String email, String department) {
        super(userId, name, email);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String getUserType() {
        return "Staff";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Department: %s", department);
    }
}