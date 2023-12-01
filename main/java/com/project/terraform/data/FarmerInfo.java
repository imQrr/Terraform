package com.project.terraform.data;

// Class representing Farmer Information.
public class FarmerInfo {

    // Private fields for email, password, and name.
    private String email = null, password = null, name = null;

    // Default constructor.
    public FarmerInfo() {
    }

    // Constructor with email, password, and name parameters.
    public FarmerInfo(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    // Constructor with email and password parameters.
    public FarmerInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Method to perform login verification.
    public boolean login(String email, String password){
        // Compare input email and password with stored email and password for successful login.
        // Ignore case for email, password enforces strict upper-lower case rules.
        return email.equalsIgnoreCase(this.email) && password.equals(this.password); // Login successful or failed.
    }

    // Getter method for the name.
    public String getName(){
        return this.name;
    }

    // Getter method for email.
    public String getEmail() {
        return email;
    }

    // Getter method for password.
    public String getPassword() {
        return password;
    }
}
