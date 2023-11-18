package com.example.togethercalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserEditProfileActivity extends AppCompatActivity {

    EditText usernameEntry,emailEntry, phoneEntry, availEntry, locationEntry, aboutEntry;
    Button saveButton, cancelButton;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_profile);
        String username = getIntent().getStringExtra("USERNAME");
        // Assuming you have these elements in your layout XML file
        usernameEntry = (EditText) findViewById(R.id.username_prof_EditText);
        emailEntry = (EditText) findViewById(R.id.email_prof_EditText);
        phoneEntry = findViewById(R.id.mobile_prof_EditText);
        availEntry = findViewById(R.id.available_prof_EditText);
        locationEntry = findViewById(R.id.location_prof_EditText);
        aboutEntry = findViewById(R.id.info_prof_EditText);
        saveButton = findViewById(R.id.save_Btn);
        cancelButton = findViewById(R.id.cancel_btn);
        DB = new DBHelper(this);

        // Populate UI elements with existing profile data the username,email,mobile,etc. from previous UserProfileActivity
        Intent intent = getIntent();
        if (intent.hasExtra("USERNAME")) {
            // Retrieve user data from DBHelper
            UserData userData = DB.getUserData(username);

            // Populate TextViews with user data
            if (userData != null) {
                usernameEntry.setText(userData.getUsername());
                emailEntry.setText(userData.getEmail());
                phoneEntry.setText(userData.getMobile());
                availEntry.setText(userData.getAvailability());
                locationEntry.setText(userData.getLocation());
                aboutEntry.setText(userData.getAbout());
            }
        }

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
        Boolean update = DB.updateUserData(username, newEmail, newPhone, newAvail, newLocation, newAbout);

        // Display a success message if updated to DB
        if (update) {
            Toast.makeText(UserEditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(UserEditProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(getApplicationContext(),UserProfileActivity.class);
        String usernameProf = getIntent().getStringExtra("USERNAME");
        intent.putExtra("USERNAME",usernameProf);
        startActivity(intent);
    }
}
