package com.example.togethercalendar;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LoginTest.db";
    private static final String TABLE_NAME = "users";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    private static final String COL_EMAIL = "email";
    private static final String COL_PHONE = "phone";
    private static final String COL_AVAIL = "avail";
    private static final String COL_LOCATION = "location";
    private static final String COL_ABOUT = "about";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (" + COL_USERNAME + " TEXT PRIMARY KEY NOT NULL, " +
                COL_PASSWORD + " TEXT NOT NULL, " +
                COL_EMAIL + " TEXT, " +
                COL_PHONE + " TEXT, " +
                COL_AVAIL + " TEXT, " +
                COL_LOCATION + " TEXT, " +
                COL_ABOUT + " TEXT" +
                ");";
        db.execSQL(createTableQuery);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // TODO: 11/16/2023 Remove and implement something like below where it takes in all the user Inputs
    public boolean insertUserData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_PASSWORD, password);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    // TODO: 11/16/2023 Implement for UserProfile
    public boolean insertUserProfileData(String newEmail, String newPhone, String newAvail,
                                         String newLocation, String newAbout) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_EMAIL, newEmail);
        contentValues.put(COL_PHONE, newPhone);
        contentValues.put(COL_PHONE, newAvail);
        contentValues.put(COL_PHONE, newLocation);
        contentValues.put(COL_PHONE, newAbout);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    // TODO: 11/16/2023 Implement a getUserData to show User their previous profile inputs


    public boolean updateUserData(String username, String email, String phone, String avail, String location, String about) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_EMAIL, email);
        contentValues.put(COL_PHONE, phone);
        contentValues.put(COL_AVAIL, avail);
        contentValues.put(COL_LOCATION, location);
        contentValues.put(COL_ABOUT, about);

        int result = db.update(TABLE_NAME, contentValues, COL_USERNAME + " = ?", new String[]{username});
        return result > 0;
    }


    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_USERNAME + " = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Method to retrieve the hashed password based on the username
    @SuppressLint("Range")
    public String getStoredPassword(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_PASSWORD + " FROM " + TABLE_NAME + " WHERE " + COL_USERNAME + " = ?", new String[]{username});

        String storedPassword = null;
        if (cursor.moveToFirst()) {
            storedPassword = cursor.getString(cursor.getColumnIndex(COL_PASSWORD));
        }

        cursor.close();
        return storedPassword;
    }

}
