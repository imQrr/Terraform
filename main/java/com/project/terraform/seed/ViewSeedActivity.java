package com.project.terraform.seed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.project.terraform.R;
import com.project.terraform.adapters.CheckSeedAdapter;
import com.project.terraform.data.SeedData;
import com.project.terraform.databases.SeedDBHandler;

import java.util.ArrayList;

public class ViewSeedActivity extends AppCompatActivity {

    Button searchButton;
    EditText searchBar;
    ListView seedView;
    ArrayList<SeedData> seedArrayList;
    ArrayList<SeedData> tempSeedList;
    CheckSeedAdapter seedAdapter;
    CheckSeedAdapter searchAdapter; // Adapter for search results
    boolean searched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_seed);

        searchButton = findViewById(R.id.searchSeedButton);
        searchBar = findViewById(R.id.searchBar);

        SeedDBHandler seedDbHandler = new SeedDBHandler(this);

        seedArrayList = seedDbHandler.readSeeds();

        seedView = findViewById(R.id.seedsList);
        seedAdapter = new CheckSeedAdapter(this, seedArrayList);
        seedView.setAdapter(seedAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = String.valueOf(searchBar.getText());

                // Check if the search bar is empty
                if (searchText.isEmpty()) {
                    // Reset the ListView to the original adapter and array list
                    seedView.setAdapter(seedAdapter);
                    searched = false;
                    return;
                }
                searched = true;
                tempSeedList = new ArrayList<SeedData>();

                // Iterate through the original list and add matching items to the temporary list
                for (SeedData seed : seedArrayList) {
                    if (seed.getSeedName().equalsIgnoreCase(searchText)) {
                        tempSeedList.add(seed);
                    }
                }

                // Create a new adapter for search results
                searchAdapter = new CheckSeedAdapter(ViewSeedActivity.this, tempSeedList);
                seedView.setAdapter(searchAdapter);

                // Show appropriate Toast message based on search results
                if (!tempSeedList.isEmpty()) {
                    Toast.makeText(ViewSeedActivity.this, searchText + " found in storage.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ViewSeedActivity.this, searchText + " does not exist in storage.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        seedView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (searched){
                    SeedData selectedSeed = searchAdapter.getItem(position);
                    String seedName = selectedSeed.getSeedName();
                    String seedExp = selectedSeed.getExpirationDate();
                    seedDbHandler.deleteSeed(seedName, seedExp);

                    tempSeedList.remove(selectedSeed);
                    seedArrayList.remove(selectedSeed);
                    seedAdapter.notifyDataSetChanged();
                    searchAdapter.notifyDataSetChanged();

                    return true;
                }
                String seedName = seedAdapter.getItem(position).getSeedName();
                String seedExp = seedAdapter.getItem(position).getExpirationDate();

                // Delete the seed from the database
                seedDbHandler.deleteSeed(seedName, seedExp);
                // Remove the item from the ArrayList
                seedArrayList.remove(seedAdapter.getItem(position));
                // Notify the adapter that the dataset has changed
                seedAdapter.notifyDataSetChanged();

                Toast.makeText(ViewSeedActivity.this, "Deleted: " + seedName, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        // Add TextWatcher to reset the ListView when the search bar is cleared
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    // Reset the ListView to the original adapter and array list
                    seedView.setAdapter(seedAdapter);
                    searched = false;
                }
            }
        });
    }
}