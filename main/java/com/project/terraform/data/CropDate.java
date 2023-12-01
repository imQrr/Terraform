package com.project.terraform.data;

// Class representing planting and harvest dates of a crop.
public class CropDate {

    private String plantingDate, harvestDate;

    // Constructor to initialize planting and harvest dates.
    public CropDate(String plantingDate, String harvestDate) {
        this.plantingDate = plantingDate;
        this.harvestDate = harvestDate;
    }

    // Default constructor.
    public CropDate(){}

    // Getter and setter methods for planting date.
    public String getPlantingDate() {
        return plantingDate;
    }

    public void setPlantingDate(String plantingDate) {
        this.plantingDate = plantingDate;
    }

    // Getter and setter methods for harvest date.
    public String getHarvestDate() {
        return harvestDate;
    }

    public void setHarvestDate(String harvestDate) {
        this.harvestDate = harvestDate;
    }
}
