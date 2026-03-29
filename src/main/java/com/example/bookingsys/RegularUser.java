package com.example.bookingsys;

public class RegularUser extends User {

    public RegularUser(String userId, String name, String email) {
        super(userId, name, email);
    }

    @Override
    public String getUserType() {
        return "Regular";
    }
}