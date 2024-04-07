package com.example.hydroapp;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentSender;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.button.MaterialButton;

public class SignUpPage extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TextView textView;

    private ImageView imageView;


    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private static final int REQ_ONE_TAP=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);



        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.repassword);

        confirmPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // Compare passwords when text is changed in the confirm password field
                comparePasswords();
            }
        });


        textView = findViewById(R.id.info);
        imageView = findViewById(R.id.google_btn);




        dbHelper = new DatabaseHelper(this);

        EditText username = findViewById(R.id.username);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);

        MaterialButton regbtn = findViewById(R.id.signupbtn);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = username.getText().toString();
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                if (!usernameText.isEmpty() && !emailText.isEmpty() && !passwordText.isEmpty()) {
                    saveUser(usernameText, emailText, passwordText);
                    User user = dbHelper.getUser(usernameText);
                    if (user != null) {
                        // User found, do something with user object
                        String email = user.getEmail();
                        String password = user.getPassword();
                        Toast.makeText(SignUpPage.this, "User with email "+email+" registered successfully!", Toast.LENGTH_SHORT).show();

                        // Navigate to SignIn activity
                        Intent intent = new Intent(SignUpPage.this, SignIn.class);
                        startActivity(intent);
                        finish(); // Optional: Finish MainActivity so it doesn't appear in the back stack
                    } else {
                        // User not found
                    }
                } else {
                    Toast.makeText(SignUpPage.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void saveUser(String username, String email, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME_USERNAME, username);
        values.put(UserContract.UserEntry.COLUMN_NAME_EMAIL, email);
        values.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, password);
        db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }


    private void comparePasswords() {
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (!password.equals(confirmPassword)) {
            // Passwords don't match, set red background color to confirm password field
            confirmPasswordEditText.setBackgroundColor(Color.RED);
            changeDrawableLeft(confirmPasswordEditText, R.drawable.baseline_warning_incorrect);

        } else {
            // Passwords match, set default background color to confirm password field
            confirmPasswordEditText.setBackgroundColor(Color.GREEN);
            changeDrawableLeft(confirmPasswordEditText, R.drawable.baseline_check_24);
        }



    }

    private void changeDrawableLeft(EditText editText, int drawableResId) {
        // Get the drawable from resources
        Drawable drawable = getResources().getDrawable(drawableResId);

        // Set the bounds of the drawable
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        // Set the drawable to the left of the EditText
        editText.setCompoundDrawables(drawable, null, null, null);
    }
}
