package com.example.hydroapp;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {

    WebView myWeb;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //myWeb = findViewById(R.id.myWeb);
        myWeb.getSettings().setJavaScriptEnabled(true);

        myWeb.setVerticalScrollBarEnabled(true);
        myWeb.setHorizontalScrollBarEnabled(true);

        myWeb.setWebChromeClient(new WebChromeClient());
        myWeb.loadUrl("http://41.193.5.154:23500/ui");

        btnBack = findViewById(R.id.btnMenu);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this, "Back to main menu", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Text Gradient
        TextView textView = findViewById(R.id.tvDashboard);

        int startColor = getResources().getColor(R.color.gradient_start);
        int endColor = getResources().getColor(R.color.gradient_end);

        Shader shader = new LinearGradient(
                0, 0, textView.getTextSize(), 0,
                startColor, endColor,
                Shader.TileMode.CLAMP);

        textView.getPaint().setShader(shader);
    }
}