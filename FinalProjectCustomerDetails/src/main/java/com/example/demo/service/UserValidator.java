package com.example.demo.service;

public class UserValidator {
    public boolean validateUserName(String fName, String lName) {

        if (fName != null && lName != null) {
            return true;
        }
        return false;
    }

    public boolean validateEmail(String emailId) {

        if (emailId != null && emailId.contains("@")) {
            return true;
        }
        return false;
    }
}

