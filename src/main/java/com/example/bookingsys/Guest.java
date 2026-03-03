package com.example.bookingsys;

public class Guest extends User {
    private String organization;

    public Guest(String userId, String name, String email, String organization) {
        super(userId, name, email);
        this.organization = organization;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public String getUserType() {
        return "Guest";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Organization: %s", organization);
    }
}