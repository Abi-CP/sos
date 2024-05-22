package com.example.sos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddContact extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextPhone;
    private Button buttonSave;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        buttonSave = findViewById(R.id.buttonSave);
        dbHelper = new DatabaseHelper(this);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String phoneNumber = editTextPhone.getText().toString();

                if (!name.isEmpty() && !phoneNumber.isEmpty()) {
                    long newRowId = dbHelper.insertContact(name, phoneNumber);
                    if (newRowId != -1) {
                        Toast.makeText(AddContact.this, "Contact saved successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddContact.this, MenuActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AddContact.this, "Failed to save contact", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddContact.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
