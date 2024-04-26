package com.example.smd_ass_3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class DatabaseHelper {

    private final String DATABASE_NAME = "PasswordDB";
    private final int DATABASE_VERSION = 1;

    private final String TABLE_NAME = "UserDetails";
    private final String KEY_ID = "_id";
    private final String KEY_NAME = "_name";
    private final String KEY_PASSWORD = "_password";


    CreateDataBase helper;
    SQLiteDatabase database;
    Context context;

    public DatabaseHelper(Context context)
    {
        this.context = context;

    }

    public void updateContact(int id, String newName, String newPassword)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, newName);
        cv.put(KEY_PASSWORD, newPassword);

        int records = database.update(TABLE_NAME, cv, KEY_ID+"=?", new String[]{id+""});
        if(records>0)
        {
            Toast.makeText(context, "User Registered", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Did not Register", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteContact(int id)
    {
        int rows = database.delete(TABLE_NAME, KEY_ID+"=?", new String[]{id+""});
        if(rows>0)
        {
            Toast.makeText(context, "User deleted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "User not deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public void insert(String name, String password)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_PASSWORD,password);

        long records = database.insert(TABLE_NAME, null, cv);
        if(records == -1)
        {
            Toast.makeText(context, "Data not inserted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Total "+records+" users added", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<User> readAllContacts()
    {
        ArrayList<User> records = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        int id_Index = cursor.getColumnIndex(KEY_ID);
        int name_Index = cursor.getColumnIndex(KEY_NAME);
        int phone_Index = cursor.getColumnIndex(KEY_PASSWORD);

        if(cursor.moveToFirst())
        {
            do{
                User u = new User();

                u.setId(cursor.getInt(id_Index));
                u.setName(cursor.getString(name_Index));
                u.setPassword(cursor.getString(phone_Index));

                records.add(u);
            }while(cursor.moveToNext());
        }

        cursor.close();

        return records;
    }

    public void open()
    {
        helper = new CreateDataBase(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = helper.getWritableDatabase();
    }

    public void close()
    {
        database.close();
        helper.close();
    }

    private class CreateDataBase extends SQLiteOpenHelper
    {
        public CreateDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + "(" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_NAME + " TEXT NOT NULL," +
                    KEY_PASSWORD + " TEXT NOT NULL" +
                    ");";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // backup code here
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

    }
}
