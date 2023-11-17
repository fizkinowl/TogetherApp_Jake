package com.example.togethercalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// TODO: 11/16/2023 Fix crashing currently wont load and redirects back to Login page
public class UserEditProfileActivity extends AppCompatActivity {

    EditText emailEntry, phoneEntry, availEntry, locationEntry, aboutEntry;
    Button saveButton, cancelButton;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_profile);

        // Assuming you have these elements in your layout XML file
        emailEntry = findViewById(R.id.email_TextView);
        phoneEntry = findViewById(R.id.mobile_TextView);
        availEntry = findViewById(R.id.available_EditText);
        locationEntry = findViewById(R.id.location_TextView);
        aboutEntry = findViewById(R.id.info_TextView);
        saveButton = findViewById(R.id.save_Btn);
        cancelButton = findViewById(R.id.cancel_btn);
        DB = new DBHelper(this);



        // Get the username from the intent (assuming it's passed from the previous activity)
        String username = getIntent().getStringExtra("USERNAME");

        // Populate the UI elements with the existing profile data
        // Fetch the existing data from the database using username and set the text of the EditTexts

        // Set up the click listeners for the buttons
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the String values inside of Various EditText
                String email = emailEntry.getText().toString();
                String phone = phoneEntry.getText().toString();
                String avail = availEntry.getText().toString();
                String location = locationEntry.getText().toString();
                String about = aboutEntry.getText().toString();

                saveProfileChanges(username,email,phone,avail,location,about);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle cancel action (e.g., go back to the profile screen)
                finish();
            }
        });
    }

    private void saveProfileChanges(String username, String newEmail, String newPhone,
                                    String newAvail, String newLocation, String newAbout) {
        // TODO: Update the user profile in the database with the new data
        Boolean update = DB.updateUserData(username, newEmail, newPhone, newAvail, newLocation, newAbout);

        // Display a success message
        if (update) {
            Toast.makeText(UserEditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(UserEditProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }

        // Finish the activity or navigate back to the profile screen
        finish();
    }
}
