package com.example.smd_ass_3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper {

    private final String DATABASE_NAME = "PasswordDB";
    private final int DATABASE_VERSION = 3;

    private final String TABLE_NAME = "UserDetails";
    private final String KEY_ID = "_id";
    private final String KEY_NAME = "_name";
    private final String KEY_PASSWORD = "_password";

    // New table details
    private final String URL_TABLE_NAME = "UrlDetails";
    private final String URL_KEY_ID = "_id";
    private final String URL_KEY_NAME = "_name";
    private final String URL_KEY_PASSWORD = "_password";
    private final String URL_KEY_URL = "_url";

    String query = "CREATE TABLE " + TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_NAME + " TEXT NOT NULL," +
            KEY_PASSWORD + " TEXT NOT NULL" +
            ");";
    // Query to create the UrlDetails table
    String urlTableQuery = "CREATE TABLE " + URL_TABLE_NAME + "(" +
            URL_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            URL_KEY_NAME + " TEXT NOT NULL," +
            URL_KEY_PASSWORD + " TEXT NOT NULL," +
            URL_KEY_URL + " TEXT NOT NULL" +
            ");";
    CreateDataBase helper;
    SQLiteDatabase database;
    Context context;

    public DatabaseHelper(Context context) {
        this.context = context;
    }

    public void updateContact(int id, String newName, String newPassword) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, newName);
        cv.put(KEY_PASSWORD, newPassword);

        int records = database.update(TABLE_NAME, cv, KEY_ID + "=?", new String[]{id + ""});
        if (records > 0) {
            Toast.makeText(context, "User Registered", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Did not Register", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateUrl(int id, String newName, String newPassword, String newUrl) {
        // Open the database
        open();

        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, newName);
        cv.put(KEY_PASSWORD, newPassword);
        cv.put(URL_KEY_URL, newUrl);

        int records = database.update(URL_TABLE_NAME, cv, URL_KEY_ID + "=?", new String[]{id + ""});
        if (records > 0) {
            Toast.makeText(context, "URL Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "URL not Updated", Toast.LENGTH_SHORT).show();
        }

        // Close the database
        close();
    }


    public void deleteContact(int id) {
        int rows = database.delete(TABLE_NAME, KEY_ID + "=?", new String[]{id + ""});
        if (rows > 0) {
            Toast.makeText(context, "User deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "User not deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteUrl(int id) {
        int rows = database.delete(URL_TABLE_NAME, URL_KEY_ID + "=?", new String[]{String.valueOf(id)});
        if (rows > 0) {
            Toast.makeText(context, "URL deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "URL not deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public void insert(String name, String password) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_PASSWORD, password);

        long records = database.insert(TABLE_NAME, null, cv);
        if (records == -1) {
            Toast.makeText(context, "Data not inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Total " + records + " users added", Toast.LENGTH_SHORT).show();
        }
    }

    public void insertUrl(String name, String password, String url) {
        ContentValues cv = new ContentValues();
        cv.put(URL_KEY_NAME, name);
        cv.put(URL_KEY_PASSWORD, password);
        cv.put(URL_KEY_URL, url);

        long records = database.insert(URL_TABLE_NAME, null, cv);
        if (records == -1) {
            Toast.makeText(context, "Data not inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Total " + records + " URLs added", Toast.LENGTH_SHORT).show();
        }

        Log.d("DatabaseHelper", "insertUrl() called with name: " + name + ", password: " + password + ", url: " + url);
    }



    public ArrayList<User> readAllContacts() {
        ArrayList<User> records = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        int id_Index = cursor.getColumnIndex(KEY_ID);
        int name_Index = cursor.getColumnIndex(KEY_NAME);
        int phone_Index = cursor.getColumnIndex(KEY_PASSWORD);

        if (cursor.moveToFirst()) {
            do {
                User u = new User();

                u.setId(cursor.getInt(id_Index));
                u.setName(cursor.getString(name_Index));
                u.setPassword(cursor.getString(phone_Index));

                records.add(u);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return records;
    }

    // Method to read all URL records
    public ArrayList<User> readAllUrls() {
        ArrayList<User> records = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + URL_TABLE_NAME, null);
        int idIndex = cursor.getColumnIndex(URL_KEY_ID);
        int nameIndex = cursor.getColumnIndex(URL_KEY_NAME);
        int passwordIndex = cursor.getColumnIndex(URL_KEY_PASSWORD);
        int urlIndex = cursor.getColumnIndex(URL_KEY_URL);

        if (cursor.moveToFirst()) {
            do {
                User u = new User();
                u.setId(cursor.getInt(idIndex));
                u.setName(cursor.getString(nameIndex));
                u.setPassword(cursor.getString(passwordIndex));
                // Add URL column data to the User object
                u.setUrl(cursor.getString(urlIndex));
                records.add(u);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return records;
    }

    public void open() {
        helper = new CreateDataBase(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = helper.getWritableDatabase();
    }

    public void close() {
        database.close();
        helper.close();
    }

    private class CreateDataBase extends SQLiteOpenHelper {
        public CreateDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {


            // Execute the queries to create the tables
            db.execSQL(query);
            db.execSQL(urlTableQuery);


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // backup code here
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + URL_TABLE_NAME);
            onCreate(db);
        }

    }

}
