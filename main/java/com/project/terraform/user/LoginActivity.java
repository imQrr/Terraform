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

    EditText emailText, passwordText;
    Button loginButton, backButton;
    UserDBHandler userDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.emailTextLogin);
        passwordText = findViewById(R.id.passwordTextLogin);

        loginButton = findViewById(R.id.confirmLogin);
        backButton = findViewById(R.id.backButtonLogin);

        userDBHandler = new UserDBHandler(LoginActivity.this);

        String intentEmail = getIntent().getStringExtra("EMAIL");
        if (intentEmail != null)
            emailText.setText(intentEmail);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(emailText.getText()),
                        password = String.valueOf(passwordText.getText());

                FarmerInfo farmerLogin = new FarmerInfo(email.toLowerCase(), password);

                if (userDBHandler.checkLogin(farmerLogin)) {
                    String name = userDBHandler.getName(farmerLogin);
                    Intent intent = new Intent(LoginActivity.this, FarmManagementActivity.class);
                    intent.putExtra("FARMER_NAME", name);
                    Toast.makeText(LoginActivity.this, "Log in successful!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}