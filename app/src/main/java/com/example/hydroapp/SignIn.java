package com.example.hydroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;

public class SignIn extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        dbHelper = new DatabaseHelper(this);

        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);

        MaterialButton signInButton = findViewById(R.id.signinbtn);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                User user = dbHelper.getUser(username);
                if (user != null && password.equals(user.getPassword())) {
                    // User exists, proceed with sign-in logic
                    Toast.makeText(SignIn.this, "Sign in successful! Username: "+user.getUsername(), Toast.LENGTH_SHORT).show();
                    // Navigate to Main activity
                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Optional: Finish MainActivity so it doesn't appear in the back stack
                } else {
                    // User does not exist or incorrect credentials
                    Toast.makeText(SignIn.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

