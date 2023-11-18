package com.example.togethercalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
public class UserProfileActivity extends AppCompatActivity {

    TextView usernameTextView, emailTextView, mobileTextView, availableTextView, locationTextView, aboutTextView;
    Button editButton;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_activity);
        String NAME = getIntent().getStringExtra("USERNAME");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menuProfile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menuProfile) {
                return true;
            } else if (id == R.id.menuCalendar) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("USERNAME",NAME));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (id == R.id.menuHome) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class).putExtra("USERNAME",NAME));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (id == R.id.menuSettings) {
                startActivity(new Intent(getApplicationContext(), UserEditProfileActivity.class).putExtra("USERNAME",NAME));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else {
                return false;
            }
        });

        //Changes textUserName to logged in usersName
        Intent i = getIntent();
        String name = i.getStringExtra("USERNAME");
        ((TextView)findViewById(R.id.username_prof_EditText)).setText(name);

        // Initialize DBHelper
        DB = new DBHelper(this);

        // Initialize TextViews
        usernameTextView = (TextView)findViewById(R.id.textUsername_Profile);
        emailTextView = (TextView)findViewById(R.id.email_prof_EditText);
        mobileTextView = (TextView)findViewById(R.id.mobile_prof_EditText);
        availableTextView = (TextView)findViewById(R.id.available_TextView);
        locationTextView = (TextView)findViewById(R.id.location_prof_EditText);
        aboutTextView = (TextView)findViewById(R.id.info_prof_EditText);
        editButton = (Button) findViewById(R.id.edit_Btn);

        Intent intent = getIntent();
        if (intent.hasExtra("USERNAME")) {
            String username = intent.getStringExtra("USERNAME");

            // Retrieve user data from DBHelper
            UserData userData = DB.getUserData(username);

            // Populate TextViews with user data
            if (userData != null) {
                usernameTextView.setText(userData.getUsername());
                emailTextView.setText(userData.getEmail());
                mobileTextView.setText(userData.getMobile());
                availableTextView.setText(userData.getAvailability());
                locationTextView.setText(userData.getLocation());
                aboutTextView.setText(userData.getAbout());
            }
        }

        // Query DB of user data to be passed for Editing Profile.This is done so that when user is
        // editing the entry fields won't be empty and knows what their previous entries are.
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String username = intent.getStringExtra("USERNAME");
                UserData userData = DB.getUserData(username);

                Intent i = new Intent(getApplicationContext(),UserEditProfileActivity.class);
                i.putExtra("USERNAME",username);
                startActivity(i);
            }
        });
    }
}