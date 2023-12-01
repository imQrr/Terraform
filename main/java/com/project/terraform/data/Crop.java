package com.project.terraform.data;

import android.graphics.Bitmap;
public class Crop {

    private String cropName = null, cropArea = null, cropDate = null;
    private byte[] bitmap_array;
    private Bitmap image;

    public Crop(){}
    public Crop(String cropName, String cropArea, String cropDate, byte[] bitmap_array) {
        this.cropName = cropName;
        this.cropArea = cropArea;
        this.cropDate = cropDate;
        this.bitmap_array = bitmap_array;
    }

    public Crop(String cropName, String cropArea, String cropDate, byte[] bitmap_array, Bitmap image) {
        this.cropName = cropName;
        this.cropArea = cropArea;
        this.cropDate = cropDate;
        this.bitmap_array = bitmap_array;
        this.image = image;
    }

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
