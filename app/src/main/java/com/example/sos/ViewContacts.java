package com.example.sos;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewContacts extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ContactsAdapter contactsAdapter;
    private List<Contact> contactList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);

        recyclerView = findViewById(R.id.viewRecyclerView);
        contactList = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);

        // Fetch contacts from the database
        contactList = dbHelper.getAllContacts();

        // Set up RecyclerView with ContactsAdapter
        contactsAdapter = new ContactsAdapter(contactList, dbHelper, false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(contactsAdapter);
    }
}
