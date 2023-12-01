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

    EditText nameText, emailText, passwordText, confirmPassword;
    Button backButton, registerButton;
    UserDBHandler userDBHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameText = findViewById(R.id.nameText);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        confirmPassword = findViewById(R.id.confirmPasswordText);
        backButton = findViewById(R.id.backButton);
        registerButton = findViewById(R.id.confirmRegisteration);

        userDBHandler = new UserDBHandler(RegisterActivity.this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(nameText.getText()),
                        email = String.valueOf(emailText.getText()),
                        password = String.valueOf(passwordText.getText()),
                        confirmPass = String.valueOf(confirmPassword.getText());

                    if (password.equals(confirmPass)){
                        FarmerInfo newFarmer = new FarmerInfo(email.toLowerCase(), password, name);

                        if (!userDBHandler.checkUser(newFarmer)) {
                            userDBHandler.addUser(newFarmer);
                            Toast.makeText(RegisterActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.putExtra("EMAIL", email);
                            finish();
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(RegisterActivity.this, "Account with that email already exists", Toast.LENGTH_SHORT).show();
                    }
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