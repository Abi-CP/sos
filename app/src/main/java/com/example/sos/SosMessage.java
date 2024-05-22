// SosMessage.java
package com.example.sos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SosMessage extends AppCompatActivity {
    private EditText editTextSosMessage;
    private Button buttonSendSos;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos_message);

        // Initialize views
        editTextSosMessage = findViewById(R.id.editTextSosMessage);
        buttonSendSos = findViewById(R.id.buttonSendSos);
        dbHelper = new DatabaseHelper(this);

        // Fetch the SOS message from the database
        String sosMessage = dbHelper.getSosMessage();
        editTextSosMessage.setText(sosMessage);

        // Set click listener for the button
        buttonSendSos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the modified SOS message
                String newSosMessage = editTextSosMessage.getText().toString().trim();

                // Update the SOS message in the database
                dbHelper.updateSosMessage(newSosMessage);

                // Display a toast message
                Toast.makeText(SosMessage.this, "SOS message updated: " + newSosMessage, Toast.LENGTH_SHORT).show();

                // Set intent to MenuActivity
                Intent intent = new Intent(SosMessage.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
