package com.example.togethercalendar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DBCalendar extends SQLiteOpenHelper {
    private static final String EVENT_TABLE_NAME = "events";
    private static final String COL_EVENT_ID = "event_id";
    private static final String COL_USERNAME = "username";
    private static final String COL_EVENT_TITLE = "event_title";
    private static final String COL_EVENT_DATE = "event_date";
    private static final String COL_EVENT_TIME = "event_time";

    public DBCalendar(Context context) {
        super(context, EVENT_TABLE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a table for events
        String createEventTableQuery = "CREATE TABLE IF NOT EXISTS " + EVENT_TABLE_NAME +
                " (" + COL_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, " +
                COL_EVENT_TITLE + " TEXT, " +
                COL_EVENT_DATE + " TEXT, " +
                COL_EVENT_TIME + " TEXT" +
                ");";
        db.execSQL(createEventTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE_NAME);
        onCreate(db);
    }

    //Add events that a user creates into DB
    public boolean insertEvent(String username, String eventTitle,  LocalDate eventDate, LocalTime eventTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_EVENT_TITLE, eventTitle);
        contentValues.put(COL_EVENT_DATE, eventDate.toString());  // Convert LocalDate to string
        contentValues.put(COL_EVENT_TIME, eventTime.toString());  // Convert LocalTime to string

        long result = db.insert(EVENT_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    //get All Events by Username
    public List<Event> getAllEvents(String username) {
        ArrayList<Event> eventsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns to retrieve
        String[] columns = {COL_EVENT_ID, COL_EVENT_TITLE, COL_EVENT_DATE, COL_EVENT_TIME};

        // Define the selection criteria
        String selection = COL_USERNAME + "=?";
        String[] selectionArgs = {username};

        // Query the database
        Cursor cursor = db.query(EVENT_TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        // Process the results
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int eventId = cursor.getInt(cursor.getColumnIndex(COL_EVENT_ID));
                @SuppressLint("Range") String eventTitle = cursor.getString(cursor.getColumnIndex(COL_EVENT_TITLE));
                @SuppressLint("Range") String eventDateString = cursor.getString(cursor.getColumnIndex(COL_EVENT_DATE));
                @SuppressLint("Range") String eventTimeString = cursor.getString(cursor.getColumnIndex(COL_EVENT_TIME));

                Event event = new Event(eventTitle, eventDateString, eventTimeString);

                // Add the Event object to the list
                eventsList.add(event);
            } while (cursor.moveToNext());

            // Close the cursor to avoid memory leaks
            cursor.close();
        }
        return eventsList;
    }


    // Retrieve all calendar events
    public Cursor getAllEvents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + EVENT_TABLE_NAME, null);
    }

    // Retrieve a specific event based on event ID
    public Cursor getEventById(int eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + EVENT_TABLE_NAME + " WHERE " + COL_EVENT_ID + " = ?", new String[]{String.valueOf(eventId)});
    }

}
