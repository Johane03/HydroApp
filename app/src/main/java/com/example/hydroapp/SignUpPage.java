package com.example.hydroapp;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentSender;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


import android.view.MotionEvent;


public class SignUpPage extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TextView textView;

    private ImageView imageView;


    private EditText passwordEditText;
    private EditText confirmPasswordEditText;



    private Boolean passwordVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.signup);

        passwordEditText = findViewById(R.id.etPassword);
        confirmPasswordEditText = findViewById(R.id.etConfirmPassword);

        passwordEditText = findViewById(R.id.etPassword);
        confirmPasswordEditText = findViewById(R.id.etConfirmPassword);

        // Add TextWatcher to etConfirmPassword
        confirmPasswordEditText.addTextChangedListener(confirmPasswordWatcher);











        dbHelper = new DatabaseHelper(this);

        EditText username = findViewById(R.id.etUsername);
        EditText email = findViewById(R.id.etEmail);


        MaterialButton regbtn = findViewById(R.id.btnRegister);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = username.getText().toString();
                String emailText = email.getText().toString();
                String passwordText = passwordEditText.getText().toString();

                if (!usernameText.isEmpty() && !emailText.isEmpty() && !passwordText.isEmpty()) {
                    saveUser(usernameText, emailText, passwordText);
                    User user = dbHelper.getUser(usernameText);
                    if (user != null) {
                        // User found, do something with user object
                        String email = user.getEmail();
                        String password = user.getPassword();
                        Toast.makeText(SignUpPage.this, "User with email " + email + " registered successfully!", Toast.LENGTH_SHORT).show();

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

        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpPage.this, SignIn.class));
            }
        });

        // Text Gradient
        TextView textView = findViewById(R.id.tvSignUp);

        int startColor = getResources().getColor(R.color.gradient_start);
        int endColor = getResources().getColor(R.color.gradient_end);

        Shader shader = new LinearGradient(
                0, 0, 0, textView.getTextSize(),
                startColor, endColor,
                Shader.TileMode.CLAMP);

        textView.getPaint().setShader(shader);






    }

    private TextWatcher confirmPasswordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if(!passwordEditText.getText().toString().isEmpty()) {
                // Check if password and confirm password match
                if (passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())) {
                    // If passwords match, set outline color to green
                    confirmPasswordEditText.setBackgroundResource(R.drawable.textview_gradient_green);
                } else {
                    // If passwords don't match, set outline color to red
                    confirmPasswordEditText.setBackgroundResource(R.drawable.textview_gradient_red);
                }

            }

        }
    };









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





    private void changeDrawableLeft(EditText editText, int drawableResId) {
        // Get the drawable from resources
        Drawable drawable = getResources().getDrawable(drawableResId);

        // Set the bounds of the drawable
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        // Set the drawable to the left of the EditText
        editText.setCompoundDrawables(drawable, null, null, null);
    }




}
