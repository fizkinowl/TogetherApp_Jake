package com.example.togethercalendar;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LoginTest1.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "users";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    private static final String COL_EMAIL = "email";
    private static final String COL_PHONE = "phone";
    private static final String COL_AVAIL = "avail";
    private static final String COL_LOCATION = "location";
    private static final String COL_ABOUT = "about";
    private static final String COL_PROFILE_IMAGE = "profile_image";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
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
                COL_PROFILE_IMAGE + " BLOB" + // New column for profile images
                ");";
        db.execSQL(createTableQuery);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        if (oldVersion < 2) {
            // Perform upgrade tasks for version 2 (e.g., add new columns)
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COL_PROFILE_IMAGE + " BLOB");
        }
    }


    // Insert User Registration inputs into DB
    public boolean insertUserData(String username, String password, String email, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_PASSWORD, password);
        contentValues.put(COL_EMAIL, email);
        contentValues.put(COL_PHONE, mobile);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    // TODO: 11/17/2023 Add changes to get userImage
    // Method to get User Data and populate the UserProfileActivity
    public UserData getUserData(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_USERNAME, COL_EMAIL, COL_PHONE, COL_AVAIL, COL_LOCATION, COL_ABOUT};
        String selection = COL_USERNAME + "=?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        UserData userData = null;

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(COL_EMAIL));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(COL_PHONE));
            @SuppressLint("Range") String avail = cursor.getString(cursor.getColumnIndex(COL_AVAIL));
            @SuppressLint("Range") String location = cursor.getString(cursor.getColumnIndex(COL_LOCATION));
            @SuppressLint("Range") String about = cursor.getString(cursor.getColumnIndex(COL_ABOUT));

            userData = new UserData(username, email, phone, avail, location, about);
        }

        cursor.close();
        return userData;
    }

    // TODO: 11/17/2023 Add changes to update userImage
    //Method to store updated user Information and store in DB
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


    /* Registration */

    // Check Username for new user registration is not already used
    // true if already in DB table, false if not contained in DB table
    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_USERNAME + " = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    //Check Email for new user registration is not already used
    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_EMAIL + " = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    //Check Mobile# for new user registration is not already used
    public boolean checkMobile(String mobile) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_PHONE + " = ?", new String[]{mobile});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


    /**
     * Login
     */

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
