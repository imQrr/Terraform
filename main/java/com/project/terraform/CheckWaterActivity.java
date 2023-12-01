package com.project.terraform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class CheckWaterActivity extends AppCompatActivity {

    // Declarations
    private static final double MAX_WATER = 10000;
    ProgressBar pb;
    Button addWaterButton, requirement, setReq;
    EditText etWaterReq;
    LinearLayout setWaterLinLayout;
    TextView timetv;
    private CountDownTimer countdownTimer;
    private final long totalTimeInMillis = 21600000; // 6 hours in milliseconds.
    private final long countdownInterval = 1; // 1 millisecond interval.
    boolean show_hide = false;
    private double waterReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_water);

        // Initialization and finding from the check_water layout.
        pb = findViewById(R.id.progressBar);
        requirement = findViewById(R.id.reqButton);
        setReq = findViewById(R.id.setButton);
        etWaterReq = findViewById(R.id.waterReq);
        setWaterLinLayout = findViewById(R.id.linearLayout);
        addWaterButton = findViewById(R.id.addWater);
        timetv = findViewById(R.id.timeTextView);
        startCountdown(); // Start countdown

        // Button listeners
        addWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pb.getProgress() != 0) {
                    countdownTimer.cancel(); // Cancel previous countdown.
                    pb.setProgress(100); // Fill progress bar.
                    startCountdown(); // Start new countdown.
                    Toast.makeText(CheckWaterActivity.this, "Water has been added.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        requirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle visibility of set water requirement layout.
                if (show_hide) {
                    setWaterLinLayout.setVisibility(View.INVISIBLE);
                    show_hide = false;
                } else {
                    setWaterLinLayout.setVisibility(View.VISIBLE);
                    show_hide = true;
                }
            }
        });

        setReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Functionality to set the water requirement.
                double temp = Double.parseDouble(etWaterReq.getText().toString());

                // Validate and set water requirement
                if (temp <= 0) {
                    // Display message for an invalid water requirement.
                    Toast.makeText(CheckWaterActivity.this, "Invalid water requirement. Please enter a positive value", Toast.LENGTH_SHORT).show();
                    return;
                } else if (temp >= MAX_WATER) {
                    // Display message if water requirement exceeds the maximum.
                    Toast.makeText(CheckWaterActivity.this, "Water requirement exceeds maximum.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // Set the water requirement.
                    waterReq = temp;
                    etWaterReq.setText("");
                    show_hide = false;
                    setWaterLinLayout.setVisibility(View.INVISIBLE);
                    // Show a confirmation message for the set water requirement.
                    Toast.makeText(CheckWaterActivity.this, "Water requirement set to: " + waterReq, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    // Start the countdown timer.
    private void startCountdown() {
        countdownTimer = new CountDownTimer(totalTimeInMillis, countdownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) ((millisUntilFinished / (double) totalTimeInMillis) * 100);
                pb.setProgress(progress);

                // Update remaining time TextView.
                String timeLeftFormatted = formatTime(millisUntilFinished);
                timetv.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                startCountdown(); // Restart the countdown.
            }
        }.start();
    }

    // Format remaining time in HH:MM:SS.
    private String formatTime(long millis) {
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        int hours = minutes / 60;

        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes % 60, seconds % 60);
    }

    // Reset the progress by restarting the countdown.
    private void resetProgress() {
        startCountdown();
    }
}
