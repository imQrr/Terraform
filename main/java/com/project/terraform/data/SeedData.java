package com.project.terraform.data;

public class SeedData {

    private String seedName;
    private double availableGrams;
    private String expirationDate;

    public SeedData(String seedName, double availableGrams, String expirationDate) {
        this.seedName = seedName;
        this.availableGrams = availableGrams;
        this.expirationDate = expirationDate;
    }

    public String getSeedName() {
        return seedName;
    }

    public void setSeedName(String seedName) {
        this.seedName = seedName;
    }

    public double getAvailableGrams() {
        return availableGrams;
    }

    public void setAvailableGrams(double availableGrams) {
        this.availableGrams = availableGrams;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
