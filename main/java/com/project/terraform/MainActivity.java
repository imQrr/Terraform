// Import necessary libraries and classes.
package com.project.terraform;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.project.terraform.user.LoginActivity;
import com.project.terraform.user.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    // Declare Buttons for login and register.
    Button login, register;

    // Oncreate() called once the app is launched.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the layout view for this activity.

        // Initialize and find the buttons in the XML file.
        login = findViewById(R.id.LoginButton);
        register = findViewById(R.id.RegisterButton);


        // Set an OnClickListener for the login Button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Set an OnClickListener for the register Button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}