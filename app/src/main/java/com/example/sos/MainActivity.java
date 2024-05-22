package com.example.sos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int darkRedColor = Color.parseColor("#8B0000");
    private final int redColor = Color.parseColor("#FF0000");
    private Button menuBtn;
    private Button sosBtn;
    private DatabaseHelper dbHelper;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

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

        sosBtn.setOnClickListener(v -> {
            // Get all phone numbers from SQL contacts table
            List<String> phoneNumbers = dbHelper.getAllPhoneNumbers();

            // Get the SOS message from the database
            String sosMessage = dbHelper.getSOSMessage();

            // Send SMS messages
            for (String phoneNumber : phoneNumbers) {
                sendSMS(phoneNumber, sosMessage);
            }
//            sendSMS("1234");

            // Show a toast message
            Toast.makeText(MainActivity.this, "SOS messages sent to all contacts", Toast.LENGTH_SHORT).show();
        });
    }

//    private void sendSMS(String phoneNumber) {
//        Toast.makeText(MainActivity.this, phoneNumber, Toast.LENGTH_SHORT).show();
//    }

    // Method to send SMS
    private void sendSMS(String phoneNumber, String message) {

//        Log.d("MainActivity sms check", phoneNumber + " " + message);
        try {
//            Log.d("MainActivity sms check" , "MainActivity sms check: SMS sent to " + phoneNumber + " with message: " + message);
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);

        } catch (Exception e) {
            Log.e("MainActivity sms check", "MainActivity sms check: Error sending SMS: " + e.getMessage());
            e.printStackTrace();
        }
//        Toast.makeText(MainActivity.this, phoneNumber, Toast.LENGTH_SHORT).show();
    }
}
