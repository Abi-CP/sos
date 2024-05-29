package com.example.sos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private final int backgroundColor = Color.parseColor("#282d34");
    private final int touchColor = Color.parseColor("#8cabd7");


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);



        Button menuBtnOne = findViewById(R.id.menu_btn_one);
        Button menuBtnTwo = findViewById(R.id.menu_btn_two);
        Button menuBtnThree = findViewById(R.id.menu_btn_three);
        Button menuBtnFour = findViewById(R.id.menu_btn_four);
        Button accountBtn = findViewById(R.id.accountBtn);


        super.onCreate(savedInstanceState);


        menuBtnOne.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
        menuBtnTwo.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
        menuBtnThree.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
        menuBtnFour.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));

        menuBtnOne.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                menuBtnOne.setBackgroundTintList(ColorStateList.valueOf(touchColor));
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                menuBtnOne.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
            }
            return false;
        });

        menuBtnOne.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, AddContact.class);
            startActivity(intent);
        });

        menuBtnTwo.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                menuBtnTwo.setBackgroundTintList(ColorStateList.valueOf(touchColor));
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                menuBtnTwo.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
            }
            return false;
        });

        menuBtnTwo.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ViewContacts.class);
            startActivity(intent);
        });

        menuBtnThree.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                menuBtnThree.setBackgroundTintList(ColorStateList.valueOf(touchColor));
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                menuBtnThree.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
            }
            return false;
        });

        menuBtnThree.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, DeleteContacts.class);
            startActivity(intent);
        });

        menuBtnFour.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                menuBtnFour.setBackgroundTintList(ColorStateList.valueOf(touchColor));
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                menuBtnFour.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
            }
            return false;
        });

        menuBtnFour.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, SosMessage.class);
            startActivity(intent);
        });

        accountBtn.setOnClickListener(v -> {

            mAuth = FirebaseAuth.getInstance();
            if (mAuth.getCurrentUser() == null) {
//                startActivity(new Intent(this, LoginActivity.class));
                startActivity(new Intent(this, SignUpActivity.class));
//                finish();
            }else {
                startActivity(new Intent(this, AccountActivity.class));

            }

        });

    }
}