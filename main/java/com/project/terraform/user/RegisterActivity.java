// Import necessary libraries and classes.
package com.project.terraform.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.terraform.R;
import com.project.terraform.data.FarmerInfo;
import com.project.terraform.databases.UserDBHandler;

public class RegisterActivity extends AppCompatActivity {

    // Declare EditText, Button, and UserDBHandler variables.
    EditText nameText, emailText, passwordText, confirmPassword;
    Button backButton, registerButton;
    UserDBHandler userDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Set the layout for "register" activity.

        // Initialize EditText and Button variables by finding them in the "register" layout.
        nameText = findViewById(R.id.nameText);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        confirmPassword = findViewById(R.id.confirmPasswordText);
        backButton = findViewById(R.id.backButton);
        registerButton = findViewById(R.id.confirmRegisteration);

        // Initialize UserDBHandler for user database operations.
        userDBHandler = new UserDBHandler(RegisterActivity.this);

        // Set OnClickListener for the registerButton.
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve input values from EditText fields and set them to String local variables.
                String name = String.valueOf(nameText.getText()),
                        email = String.valueOf(emailText.getText()),
                        password = String.valueOf(passwordText.getText()),
                        confirmPass = String.valueOf(confirmPassword.getText());

                // Validation check to check if the passwords match.
                if (password.equals(confirmPass)) {
                    // Create FarmerInfo object with user registration details while passing the email in lowerCase.
                    FarmerInfo newFarmer = new FarmerInfo(email.toLowerCase(), password, name);

                    // Validation Check to check if user already exists in the database.
                    if (!userDBHandler.checkUser(newFarmer)) {
                        // If user doesn't exist, add the user to the database.
                        userDBHandler.addUser(newFarmer);
                        Toast.makeText(RegisterActivity.this, "Account created!", Toast.LENGTH_SHORT).show();

                        // Create an Intent to navigate from RegisterActivity to the LoginActivity and pass the email.
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtra("EMAIL", email);
                        finish(); // Finish the current activity.
                        startActivity(intent); // Start the LoginActivity.
                    } else {
                        // If user already exists, show a message indicating the existing account.
                        Toast.makeText(RegisterActivity.this, "Account with that email already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Set OnClickListener for the backButton to finish the activity.
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish the current activity and allow user to navigate back to the LoginActivity.
            }
        });

    }
}
