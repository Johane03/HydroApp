package com.example.hydroapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardScreen extends AppCompatActivity {

    private WebView myWeb;
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        myWeb = findViewById(R.id.myWeb); //get from web component
        myWeb.getSettings().setJavaScriptEnabled(true); //set JS

        myWeb.setVerticalScrollBarEnabled(true); //vs true
        myWeb.setHorizontalScrollBarEnabled(true); //hs true

        myWeb.setWebChromeClient(new WebChromeClient()); //chrome client opens in Chrome rather than in the app
        myWeb.loadUrl("http://41.193.5.154:23500/ui"); //dashboard url for NodeRed server

        btnBack = findViewById(R.id.btnReturn); //get from button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Change MAINACTIVITY in next line to MENU for navigation --> exit from menu page, not signup
                Intent intent = new Intent(DashboardScreen.this, MainMenu.class);
                startActivity(intent); //move back to menu page
            }
        });
    }
}