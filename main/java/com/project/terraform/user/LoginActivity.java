// Import necessary libraries and classes
package com.project.terraform.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.terraform.FarmManagementActivity;
import com.project.terraform.R;
import com.project.terraform.data.FarmerInfo;
import com.project.terraform.databases.UserDBHandler;


public class LoginActivity extends AppCompatActivity {

    // Declare EditText, Button, and UserDBHandler variables.
    EditText emailText, passwordText;
    Button loginButton, backButton;
    UserDBHandler userDBHandler;

    // Method called when the activity is created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Set the layout for "login" activity

        // Initialize EditText and Button variables by finding them in the "login" layout.
        emailText = findViewById(R.id.emailTextLogin);
        passwordText = findViewById(R.id.passwordTextLogin);
        loginButton = findViewById(R.id.confirmLogin);
        backButton = findViewById(R.id.backButtonLogin);

        // Initialize UserDBHandler for user database operations.
        userDBHandler = new UserDBHandler(LoginActivity.this);

        // Retrieve email from intent and set it in the emailText field if available.
        String intentEmail = getIntent().getStringExtra("EMAIL");
        if (intentEmail != null)
            emailText.setText(intentEmail);

        // Set OnClickListener for the loginButton.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve email and password from EditText fields.
                String email = String.valueOf(emailText.getText()),
                        password = String.valueOf(passwordText.getText());

                // Create FarmerInfo object with login credentials.
                FarmerInfo farmerLogin = new FarmerInfo(email.toLowerCase(), password);

                // Check login credentials in the user database.
                if (userDBHandler.checkLogin(farmerLogin)) {
                    // If login successful, retrieve the name associated with the login.
                    String name = userDBHandler.getName(farmerLogin);
                    // Create an Intent to navigate from LoginActivity to the FarmManagementActivity and pass the name.
                    Intent intent = new Intent(LoginActivity.this, FarmManagementActivity.class);
                    intent.putExtra("FARMER_NAME", name);
                    // Display login success message and start the FarmManagementActivity.
                    Toast.makeText(LoginActivity.this, "Log in successful!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {
                    // If login unsuccessful, display an invalid credentials message.
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set OnClickListener for the backButton to finish the activity.
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Allows the user to go back to the previous activity.
            }
        });
    }
}
