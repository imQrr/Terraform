package com.project.terraform.crop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.project.terraform.R;
import com.project.terraform.adapters.PlantSeedAdapter;
import com.project.terraform.data.Crop;
import com.project.terraform.databases.PlantDBHandler;

import java.util.ArrayList;

public class ViewPlantActivity extends AppCompatActivity {

    ListView cropList;
    ArrayList<Crop> plantArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plant);

        cropList = findViewById(R.id.cropList);
        PlantDBHandler plantDbHandler = new PlantDBHandler(ViewPlantActivity.this);

        plantArrayList = plantDbHandler.readPlants();
        PlantSeedAdapter plantAdapter = new PlantSeedAdapter(ViewPlantActivity.this, plantArrayList);
        cropList.setAdapter(plantAdapter);

        cropList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String plantName = plantArrayList.get(position).getCropName();
                String plantArea = plantArrayList.get(position).getCropArea();

                // Delete the seed from the database
                plantDbHandler.deletePlant(plantArea);
                // Remove the item from the ArrayList
                plantArrayList.remove(position);
                // Notify the adapter that the dataset has changed
                plantAdapter.notifyDataSetChanged();

                Toast.makeText(ViewPlantActivity.this, "Harvested "+ plantName + " at: " + plantArea, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}