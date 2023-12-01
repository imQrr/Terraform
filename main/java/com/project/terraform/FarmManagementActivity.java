package com.project.terraform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import androidx.annotation.NonNull;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;

import com.project.terraform.crop.PlantSeedActivity;
import com.project.terraform.seed.CheckSeedActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FarmManagementActivity extends AppCompatActivity {

    // Constants, String, double, TextView, Button, ImageView variables declaration.
    private static final int PICK_IMAGE_CODE = 1;
    private String farmName, address;
    private double landArea;
    private TextView locationTextView, farmerName;
    private Button checkSeedButton, plantSeedButton, backButton, checkWaterButton;
    private ImageView farmer_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_management); // Set layout to "farm_management" layout.

        // Retrieving farmer's name from intent.
        String name = getIntent().getStringExtra("FARMER_NAME");

        // Initializing UI components.
        farmerName = findViewById(R.id.farmerName);
        farmerName.setText(name);

        // Find the these views in the "farm_management" layout.
        checkSeedButton = findViewById(R.id.seedCheck);
        plantSeedButton = findViewById(R.id.plantSeed);
        backButton = findViewById(R.id.backButtonFarm);
        farmer_image = findViewById(R.id.farmerImage);
        checkWaterButton = findViewById(R.id.checkWater);

        // Setting onClickListeners for buttons.
        checkSeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initiating intent to move from FarmManagementActivity to CheckSeedActivity.
                Intent intent = new Intent(FarmManagementActivity.this, CheckSeedActivity.class);
                startActivity(intent);
            }
        });

        plantSeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initiating intent to move from FarmManagementActivity to PlantSeedActivity.
                Intent intent = new Intent(FarmManagementActivity.this, PlantSeedActivity.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finishing current activity and moving to the previous activity.
                finish();
            }
        });

        checkWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initiating intent to move from FarmManagementActivity to CheckWaterActivity.
                Intent intent = new Intent(FarmManagementActivity.this, CheckWaterActivity.class);
                startActivity(intent);
            }
        });

        farmer_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Opening image gallery.
                openGallery();
            }
        });

        locationTextView = findViewById(R.id.locationTextView);

        // Checking and requesting location permission if needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                startLocationUpdates();
            }
        } else {
            startLocationUpdates();
        }
    }

    // Method to start location updates.
    private void startLocationUpdates() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Updating location information.
                updateLocation(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        // Requesting location updates.
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    // Method to update location information.
    private void updateLocation(Location location) {
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            // Geocoding to get address from latitude and longitude.
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses != null && addresses.size() > 0) {
                    Address address = addresses.get(0);
                    String addressText = String.format(
                            "%s, %s, %s",
                            address.getSubLocality(),
                            address.getLocality(),
                            address.getCountryName()
                    );
                    locationTextView.setText(addressText);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Handling permission request result.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        }
    }

    // Method to open image gallery.
    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Checking if the result is OK and corresponds to the request code for picking an image.
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE) {
            // Retrieving the selected image URI from the Intent data.
            Uri imageUri = data.getData();

            // Setting the selected image to the farmer's image view.
            farmer_image.setImageURI(imageUri);
        }
    }
}
