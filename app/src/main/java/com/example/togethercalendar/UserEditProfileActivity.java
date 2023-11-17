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

    EditText emailEntry, phoneEntry, availEntry, locationEntry, aboutEntry;
    Button saveButton, cancelButton;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_profile);

        // Assuming you have these elements in your layout XML file
        emailEntry = (EditText) findViewById(R.id.email_prof_EditText);
        phoneEntry = findViewById(R.id.mobile_prof_EditText);
        availEntry = findViewById(R.id.available_prof_EditText);
        locationEntry = findViewById(R.id.location_prof_EditText);
        aboutEntry = findViewById(R.id.info_prof_EditText);
        saveButton = findViewById(R.id.save_Btn);
        cancelButton = findViewById(R.id.cancel_btn);
        DB = new DBHelper(this);

        // TODO: 11/17/2023 Maybe Remove potential unsafe parse of data and implement the way in UserProfile
        // Populate UI elements with existing profile data the username,email,mobile,etc. from previous UserProfileActivity
        String username = getIntent().getStringExtra("USERNAME");
        String email = getIntent().getStringExtra("EMAIL");
        String mobile = getIntent().getStringExtra("MOBILE");
        String avail = getIntent().getStringExtra("AVAIL");
        String loc = getIntent().getStringExtra("LOC");
        String about = getIntent().getStringExtra("ABOUT");

        // Set Editable Text to be their Current Profile information.
        ((TextView)findViewById(R.id.username_prof_EditText)).setText(username);
        ((TextView)findViewById(R.id.email_prof_EditText)).setText(email);
        ((TextView)findViewById(R.id.mobile_prof_EditText)).setText(mobile);
        ((TextView)findViewById(R.id.available_prof_EditText)).setText(avail);
        ((TextView)findViewById(R.id.location_prof_EditText)).setText(loc);
        ((TextView)findViewById(R.id.info_prof_EditText)).setText(about);

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
        // TODO: 11/17/2023 ADD validation same as Registration page.
        //  Reason: Only want inputs that abide by original registration standards
        Boolean update = DB.updateUserData(username, newEmail, newPhone, newAvail, newLocation, newAbout);

        // Display a success message
        if (update) {
            Toast.makeText(UserEditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(UserEditProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }

        // Finish the activity or navigate back to the profile screen
        //finish();
        Intent intent = new Intent(getApplicationContext(),UserProfileActivity.class);
        String usernameProf = getIntent().getStringExtra("USERNAME");
        intent.putExtra("USERNAME",usernameProf);
        startActivity(intent);
    }
}
