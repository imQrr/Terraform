package com.project.terraform.data;

public class CropDate {

    private String plantingDate, harvestDate;

    public CropDate(String plantingDate, String harvestDate) {
        this.plantingDate = plantingDate;
        this.harvestDate = harvestDate;
    }

    public CropDate(){}

    public String getPlantingDate() {
        return plantingDate;
    }

    public void setPlantingDate(String plantingDate) {
        this.plantingDate = plantingDate;
    }

    public String getHarvestDate() {
        return harvestDate;
    }

    public void setHarvestDate(String harvestDate) {
        this.harvestDate = harvestDate;
    }
}
