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

public class PlantSeedAdapter extends ArrayAdapter<Crop> {
    public PlantSeedAdapter(@NonNull Context context, ArrayList<Crop> arrayList){
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View currentItemView = convertView;

        if (currentItemView == null)
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.plant_seed_list_view, parent, false);

        Crop currentCropPosition = getItem(position);

        TextView seedName = currentItemView.findViewById(R.id.cropName);
        seedName.setText(currentCropPosition.getCropName());

        TextView availableGrams = currentItemView.findViewById(R.id.plantArea);
        availableGrams.setText(currentCropPosition.getCropArea());

        TextView expirationDate = currentItemView.findViewById(R.id.plantingDate);
        expirationDate.setText(currentCropPosition.getPlantingDate());

        ImageView plantImage = currentItemView.findViewById(R.id.plantImage);
        plantImage.setImageBitmap(currentCropPosition.getImage());

        return currentItemView;
    }
}
