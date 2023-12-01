package com.project.terraform.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.terraform.R;
import com.project.terraform.data.Crop;

import java.util.ArrayList;

// Adapter class to display a custom view for each item in the plant/seed list.
public class PlantSeedAdapter extends ArrayAdapter<Crop> {

    // Constructor to initialize the adapter.
    public PlantSeedAdapter(@NonNull Context context, ArrayList<Crop> arrayList) {
        super(context, 0, arrayList);
    }

    // Method to get a view that displays data at the specified position in the data set.
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        // Inflate the layout for each row of the ListView if it's null.
        if (currentItemView == null)
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.plant_seed_list_view, parent, false);

        // Get the Crop object at the current position.
        Crop currentCropPosition = getItem(position);

        // Find the TextViews and ImageView in the  "plant_seed_list_view" layout.
        TextView seedName = currentItemView.findViewById(R.id.cropName);
        TextView availableGrams = currentItemView.findViewById(R.id.plantArea);
        TextView expirationDate = currentItemView.findViewById(R.id.plantingDate);
        ImageView plantImage = currentItemView.findViewById(R.id.plantImage);

        // Set data to the respective views from the Crop object.
        if (currentCropPosition != null) {
            seedName.setText(currentCropPosition.getCropName());
            availableGrams.setText(currentCropPosition.getCropArea());
            expirationDate.setText(currentCropPosition.getPlantingDate());
            plantImage.setImageBitmap(currentCropPosition.getImage());
        }

        return currentItemView;
    }
}
