package com.example.sos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private final int darkRedColor = Color.parseColor("#8B0000");
    private final int redColor = Color.parseColor("#FF0000");
    private Button menuBtn;
    private Button sosBtn;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuBtn = findViewById(R.id.menu_btn);
        menuBtn.setBackgroundTintList(ColorStateList.valueOf(redColor));

        sosBtn = findViewById(R.id.sos_button);
        sosBtn.setBackgroundTintList(ColorStateList.valueOf(redColor));

        menuBtn.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Button is touched
                    menuBtn.setBackgroundTintList(ColorStateList.valueOf(darkRedColor));
                    break;
                case MotionEvent.ACTION_UP:
                    // Button is released
                    menuBtn.setBackgroundTintList(ColorStateList.valueOf(redColor));
                    break;
            }
            return false;
        });

        sosBtn.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Button is touched
                    sosBtn.setBackgroundTintList(ColorStateList.valueOf(darkRedColor));
                    break;
                case MotionEvent.ACTION_UP:
                    // Button is released
                    sosBtn.setBackgroundTintList(ColorStateList.valueOf(redColor));
                    break;
            }
            return false;
        });

        menuBtn.setOnClickListener(v -> {
            // Start the new activity here
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        });
    }
}