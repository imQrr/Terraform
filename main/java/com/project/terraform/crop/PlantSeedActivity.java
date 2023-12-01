package com.project.terraform.crop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.terraform.R;
import com.project.terraform.databases.PlantDBHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// Activity for planting seeds and storing information in the database.
public class PlantSeedActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_CODE = 1;
    private Button plantButton, backButton, photoButton, viewPlantsButton;
    private EditText cropName, plantArea, plantDate;
    private Bitmap bitmap;
    byte[] bitmap_array;
    private PlantDBHandler plantDbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_seed); // Set the layout view to "plant_seed" layout.

        // Initializing views and database handler and finding them in "plant_seed" layout.
        cropName = findViewById(R.id.enterCropName);
        plantArea = findViewById(R.id.enterCropArea);
        plantDate = findViewById(R.id.enterCropPlantDate);
        plantButton = findViewById(R.id.plantSeedButton);
        backButton = findViewById(R.id.backButtonPlant);
        viewPlantsButton = findViewById(R.id.viewPlantedSeeds);
        photoButton = findViewById(R.id.imageButton);

        plantDbHandler = new PlantDBHandler(PlantSeedActivity.this); // Object PlantDBHandler

        // Click listener to open the gallery.
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // Click listener for planting a seed.
        plantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(cropName.getText()),
                        area = String.valueOf(plantArea.getText()),
                        date = String.valueOf(plantDate.getText());

                //Multiple validation checks for image, date, and area.
                if (bitmap == null)
                    Toast.makeText(PlantSeedActivity.this, "Please select an image.", Toast.LENGTH_SHORT).show();
                else if (!isValidDate(date))
                    Toast.makeText(PlantSeedActivity.this, "Please input a valid date", Toast.LENGTH_SHORT).show();
                else if (plantDbHandler.isAreaInDatabase(area))
                    Toast.makeText(PlantSeedActivity.this, "Area is already occupied", Toast.LENGTH_SHORT).show();
                else {
                    plantDbHandler.addNewPlant(name, area, date, bitmap_array);
                    Toast.makeText(PlantSeedActivity.this, "Plant record saved!", Toast.LENGTH_SHORT).show();

                    // Clearing input fields and resetting Bitmap.
                    cropName.setText("");
                    plantArea.setText("");
                    plantDate.setText("");
                    bitmap = null;
                }
            }
        });

        // Click listener for going back.
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //Allows user to go back to the previous activity.
            }
        });

        // Click listener for viewing planted seeds.
        viewPlantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlantSeedActivity.this, ViewPlantActivity.class);
                startActivity(intent); // Starts the ViewPlantActivity.
            }
        });
    }

    // Method to open the gallery for selecting images implicitly.
    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if the result is okay and the request code matches the image selection code.
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE) {
            // Get the selected image URI
            Uri imageUri = data.getData();
            try {
                // Convert the selected image URI to a Bitmap.
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                // Calculate the density of the device's screen.
                float density = getResources().getDisplayMetrics().density;

                // Define the size for the thumbnail.
                int thumbnailSize = (int) (100 * density);

                // Create a thumbnail from the Bitmap using ThumbnailUtils.
                Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, thumbnailSize, thumbnailSize);

                // Convert the thumbnail Bitmap to a byte array (PNG format).
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                bitmap_array = outputStream.toByteArray();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // Method to validate the date format.
    private boolean isValidDate(String inputDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date expirationDate = sdf.parse(inputDate);

            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false; // Invalid date format.
        }
    }
}
