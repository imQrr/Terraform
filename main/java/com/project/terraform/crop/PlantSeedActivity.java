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
        setContentView(R.layout.activity_plant_seed);

        cropName = findViewById(R.id.enterCropName);
        plantArea = findViewById(R.id.enterCropArea);
        plantDate = findViewById(R.id.enterCropPlantDate);
        plantButton = findViewById(R.id.plantSeedButton);
        backButton = findViewById(R.id.backButtonPlant);
        viewPlantsButton = findViewById(R.id.viewPlantedSeeds);
        photoButton = findViewById(R.id.imageButton);

        plantDbHandler = new PlantDBHandler(PlantSeedActivity.this);

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        plantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(cropName.getText()),
                        area = String.valueOf(plantArea.getText()),
                        date = String.valueOf(plantDate.getText());

                if (bitmap == null)
                    Toast.makeText(PlantSeedActivity.this, "Please select an image.", Toast.LENGTH_SHORT).show();

                else if (!isValidDate(date))
                    Toast.makeText(PlantSeedActivity.this, "Please input a valid date", Toast.LENGTH_SHORT).show();

                else if (plantDbHandler.isAreaInDatabase(area))
                    Toast.makeText(PlantSeedActivity.this, "Area is already occupied", Toast.LENGTH_SHORT).show();

                else {
                    plantDbHandler.addNewPlant(name, area, date, bitmap_array);
                    Toast.makeText(PlantSeedActivity.this, "Plant record saved!", Toast.LENGTH_SHORT).show();

                    cropName.setText("");
                    plantArea.setText("");
                    plantDate.setText("");
                    bitmap = null;
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPlantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlantSeedActivity.this, ViewPlantActivity.class);
                startActivity(intent);
            }
        });
    }

    public void openGallery() {
        // Get images from gallery for each plant
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE) {
            Uri imageUri = data.getData();
            try {
                // Load the image from the Uri into a Bitmap
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                float density = getResources().getDisplayMetrics().density;
                int thumbnailSize = (int) (100 * density);

                // Create a thumbnail from the Bitmap
                Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, thumbnailSize, thumbnailSize);

                // Convert the thumbnail to a byte array
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                bitmap_array = outputStream.toByteArray();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValidDate(String inputDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date expirationDate = sdf.parse(inputDate);

            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false; // Invalid date format
        }
    }
}