package com.example.sos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sos.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME_CONTACTS = "contacts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone_number";

    // New table and column for SOS message
    private static final String TABLE_NAME_MESSAGE = "message";
    private static final String COLUMN_MESSAGE_ID = "id";
    private static final String COLUMN_MESSAGE_TEXT = "message_text";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the contacts table
        String createContactsTableQuery = "CREATE TABLE " + TABLE_NAME_CONTACTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PHONE + " TEXT)";
        db.execSQL(createContactsTableQuery);

        // Create the message table
        String createMessageTableQuery = "CREATE TABLE " + TABLE_NAME_MESSAGE + "("
                + COLUMN_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_MESSAGE_TEXT + " TEXT)";
        db.execSQL(createMessageTableQuery);

        // Insert default SOS message
        insertDefaultSosMessage(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MESSAGE);
        onCreate(db);
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_PHONE
        };

        Cursor cursor = db.query(
                TABLE_NAME_CONTACTS,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
            Contact contact = new Contact(name, phoneNumber);
            contact.setId(id); // Set the contact ID
            contactList.add(contact);
        }

        cursor.close();
        db.close();
        return contactList;
    }

    public long insertContact(String name, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PHONE, phoneNumber);
        long newRowId = db.insert(TABLE_NAME_CONTACTS, null, values);
        db.close();
        return newRowId;
    }

    public void deleteContact(long contactId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_NAME_CONTACTS, COLUMN_ID + " = ?", new String[]{String.valueOf(contactId)});
        db.close();
        Log.d("DatabaseHelper", "Deleted " + deletedRows + " rows for contact ID: " + contactId);
    }

    // Method to insert the default SOS message into the database
    private void insertDefaultSosMessage(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MESSAGE_TEXT, "Hi.. I'm in EMERGENCY.. Please Help ME.!");
        db.insert(TABLE_NAME_MESSAGE, null, values);
    }

    // Method to retrieve the SOS message from the database
    public String getSosMessage() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sosMessage = "";

        Cursor cursor = db.query(
                TABLE_NAME_MESSAGE,
                new String[]{COLUMN_MESSAGE_TEXT},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            sosMessage = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MESSAGE_TEXT));
        }

        cursor.close();
        db.close();
        return sosMessage;
    }

    // Method to update the SOS message in the database
    public void updateSosMessage(String newSosMessage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MESSAGE_TEXT, newSosMessage);
        db.update(TABLE_NAME_MESSAGE, values, null, null);
        db.close();
    }
}
