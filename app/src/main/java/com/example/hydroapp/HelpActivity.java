package com.example.hydroapp;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_help);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Text Gradient
        TextView textView = findViewById(R.id.tvHelp);

        int startColor = getResources().getColor(R.color.gradient_start);
        int endColor = getResources().getColor(R.color.gradient_end);

        Shader shader = new LinearGradient(
                0, 0, textView.getTextSize(), 0,
                startColor, endColor,
                Shader.TileMode.CLAMP);

        textView.getPaint().setShader(shader);

        /*// Custom Bullet points
        TextView textView = findViewById(R.id.tvHelpText);

        // Create a SpannableString with bullet points
        String[] items = {"Item 1", "Item 2", "Item 3"};
        SpannableString spannableString = new SpannableString("");
        for (String item : items) {
            Drawable bullet = getResources().getDrawable(R.drawable.custom_bullet); // Your custom bullet point drawable
            bullet.setBounds(0, 0, bullet.getIntrinsicWidth(), bullet.getIntrinsicHeight());
            ImageSpan bulletSpan = new ImageSpan(bullet, ImageSpan.ALIGN_BASELINE);
            spannableString = new SpannableString(spannableString + "\n" + item);
            spannableString.setSpan(bulletSpan, spannableString.length() - item.length(), spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        // Set the SpannableString to the TextView
        textView.setText(spannableString);*/
    }
}