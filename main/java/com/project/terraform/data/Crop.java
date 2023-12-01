package com.project.terraform.data;

import android.graphics.Bitmap;

// Crop class representing information about a crop.
public class Crop {

    // Private fields to store crop information.
    private String cropName = null, cropArea = null, cropDate = null;
    private byte[] bitmap_array;
    private Bitmap image;

    // Default constructor.
    public Crop(){}

    // Constructor with parameters to initialize crop details without image,
    public Crop(String cropName, String cropArea, String cropDate, byte[] bitmap_array) {
        this.cropName = cropName;
        this.cropArea = cropArea;
        this.cropDate = cropDate;
        this.bitmap_array = bitmap_array;
    }

    // Constructor with parameters to initialize crop details with image.
    public Crop(String cropName, String cropArea, String cropDate, byte[] bitmap_array, Bitmap image) {
        this.cropName = cropName;
        this.cropArea = cropArea;
        this.cropDate = cropDate;
        this.bitmap_array = bitmap_array;
        this.image = image;
    }

    // Getter methods to retrieve crop information.
    public String getCropName() {
        return cropName;
    }

    public String getCropArea() {
        return cropArea;
    }

    public String getPlantingDate() {
        return cropDate;
    }

    public Bitmap getImage() {
        return image;
    }
}
