package com.project.terraform.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.terraform.R;
import com.project.terraform.data.SeedData;

import java.util.ArrayList;

// Adapter class for the SeedData ArrayList.
public class CheckSeedAdapter extends ArrayAdapter<SeedData> {

    // Constructor initializing the adapter.
    public CheckSeedAdapter(@NonNull Context context, ArrayList<SeedData> arrayList){
        super(context, 0, arrayList);
    }

    // Method to create a custom view for each item in the list.
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View currentItemView = convertView;

        // Inflate the layout if the view is null.
        if (currentItemView == null)
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.check_seed_list_view, parent, false);

        // Get the current SeedData object.
        SeedData currentSeedPosition = getItem(position);

        // Set the seed name.
        TextView seedName = currentItemView.findViewById(R.id.SeedName);
        seedName.setText(currentSeedPosition.getSeedName());

        // Set the available grams.
        TextView availableGrams = currentItemView.findViewById(R.id.GramsAmount);
        availableGrams.setText(""+currentSeedPosition.getAvailableGrams());

        // Set the expiration date.
        TextView expirationDate = currentItemView.findViewById(R.id.ExpDate);
        expirationDate.setText(currentSeedPosition.getExpirationDate());

        return currentItemView; // Return the view for display.
    }
}
