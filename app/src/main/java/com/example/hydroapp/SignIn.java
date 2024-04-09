package com.example.hydroapp;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;

public class SignIn extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        dbHelper = new DatabaseHelper(this);

        EditText usernameEditText = findViewById(R.id.etUsername);
        EditText passwordEditText = findViewById(R.id.etPassword);

        MaterialButton signInButton = findViewById(R.id.btnLogin);

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


        TextView textViewLink = findViewById(R.id.tvRedirect);

        // Create a SpannableString
        SpannableString spannableString = new SpannableString(textViewLink.getText());

        // Define ClickableSpan for "Sign up" text
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textViewLink) {
                // Handle the click event
                Intent intent = new Intent(SignIn.this, SignUpPage.class);
                startActivity(intent);
            }
        };


        spannableString.setSpan(clickableSpan, textViewLink.getText().toString().indexOf("Sign up"),
                textViewLink.getText().toString().indexOf("Sign up") + "Sign up".length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        applyTextColor(spannableString, getResources().getColor(R.color.gradient_start),
                textViewLink.getText().toString().indexOf("Sign up"),
                textViewLink.getText().toString().indexOf("Sign up") + "Sign up".length());

        textViewLink.setText(spannableString);


        textViewLink.setMovementMethod(LinkMovementMethod.getInstance());



        // Text Gradient
        TextView textView = findViewById(R.id.tvSignIn);

        int startColor = getResources().getColor(R.color.gradient_start);
        int endColor = getResources().getColor(R.color.gradient_end);

        Shader shader = new LinearGradient(
                0, 0, 0, textView.getTextSize(),
                startColor, endColor,
                Shader.TileMode.CLAMP);

        textView.getPaint().setShader(shader);
    }

    private void applyTextColor(Spannable spannable, @ColorInt int color, int start, int end) {
        spannable.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}

