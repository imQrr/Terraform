package com.project.terraform.data;

public class FarmerInfo {

    private String email = null, password = null, name = null;

    public FarmerInfo() {
    }

    public FarmerInfo(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public FarmerInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean login(String email, String password){

        //ignore case for email, password enforces strict upper-lower case rules
        return email.equalsIgnoreCase(this.email) && password.equals(this.password); // Login successful or failed

    }

    public String getName(){
        return this.name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
