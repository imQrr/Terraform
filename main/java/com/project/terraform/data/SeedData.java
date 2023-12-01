package com.project.terraform.data;

// Class representing seed data.
public class SeedData {

    // Private fields for seedName, availableGrams, and expirationDate.
    private String seedName;
    private double availableGrams;
    private String expirationDate;

    // Constructor with seedName, availableGrams, and expirationDate parameters.
    public SeedData(String seedName, double availableGrams, String expirationDate) {
        this.seedName = seedName;
        this.availableGrams = availableGrams;
        this.expirationDate = expirationDate;
    }

    // Getter method for seedName.
    public String getSeedName() {
        return seedName;
    }

    // Setter method for seedName.
    public void setSeedName(String seedName) {
        this.seedName = seedName;
    }

    // Getter method for availableGrams.
    public double getAvailableGrams() {
        return availableGrams;
    }

    // Setter method for availableGrams.
    public void setAvailableGrams(double availableGrams) {
        this.availableGrams = availableGrams;
    }

    // Getter method for expirationDate.
    public String getExpirationDate() {
        return expirationDate;
    }

    // Setter method for expirationDate.
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
