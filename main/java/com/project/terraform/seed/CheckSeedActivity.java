// Import necessary libraries and classes
package com.project.terraform.seed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.project.terraform.R;
import com.project.terraform.databases.SeedDBHandler;

public class CheckSeedActivity extends AppCompatActivity {
    // Declare EditText for seedName, availableGrams, expirationDate.
    EditText seedName, availableGrams, expirationDate;

    // Declare Button for addSeed, backButton, viewSeeds.
    Button addSeed, backButton, viewSeeds;

    // Declare SeedDBHandler to use the database handler.
    private SeedDBHandler seedDbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_seed); // Set the layout "check_seed" for CheckSeedActivity.

        // Initialize EditTexts and Button variables and finding them in the "check_seed" layout.
        seedName = findViewById(R.id.enterSeedName);
        availableGrams = findViewById(R.id.enterSeedAmount);
        expirationDate = findViewById(R.id.enterSeedExpire);
        addSeed = findViewById(R.id.addSeedButton);
        backButton = findViewById(R.id.backButtonSeed);
        viewSeeds = findViewById(R.id.viewAllSeeds);


        // Initialize seedDBHandler and call its constructor by passing this Activity as a parameter.
        seedDbHandler = new SeedDBHandler(CheckSeedActivity.this);

        addSeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Retrieve input values from EditText fields and set them to local variables.
                String name = String.valueOf(seedName.getText());
                double grams = Double.parseDouble(String.valueOf(availableGrams.getText()));
                String date = String.valueOf(expirationDate.getText());

                // Validation check to make sure user inputs both information.
                if (name.isEmpty() && date.isEmpty()) {
                    Toast.makeText(CheckSeedActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validation check to make sure teh seed's weight (in grams) is within the set limit.
                if (grams <= 0) {
                    Toast.makeText(CheckSeedActivity.this, "Grams should be greater than 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (grams >= 1000000000) {
                    Toast.makeText(CheckSeedActivity.this, "Grams should be less than 1,000,000,000", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validation check to make sure the data format is correct, and to make sure the data set is from the future
                if (!isValidDate(date)) {
                    Toast.makeText(CheckSeedActivity.this, "Invalid date format or date is in the past", Toast.LENGTH_SHORT).show();
                    return;
                }

                seedDbHandler.addNewSeed(name, grams, date);
                Toast.makeText(CheckSeedActivity.this, "Seed has been added.", Toast.LENGTH_SHORT).show();

                // Clear EditText fields after adding the seed.
                seedName.setText("");
                availableGrams.setText("");
                expirationDate.setText("");
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewSeeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckSeedActivity.this, ViewSeedActivity.class);
                startActivity(intent); // Allows user to return to the previous activity.
            }
        });

    }

    // Method to validate the date format and check if it's in the future.
    private boolean isValidDate(String inputDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date expirationDate = sdf.parse(inputDate);
            Date currentDate = Calendar.getInstance().getTime();

            // Compare dates
            return !expirationDate.before(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false; // Invalid date format
        }
    }
}