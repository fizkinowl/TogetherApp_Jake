package com.example.togethercalendar;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    TextView username;
    EditText searchTxt;
    Button searchBtn;
    LinearLayout calendar,events,chat,account;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //Change textUserName to logged in usersName
        String name = getIntent().getStringExtra("USERNAME");
        ((TextView)findViewById(R.id.textUsername)).setText(name);

        username = (TextView) findViewById(R.id.textUsername);
        // TODO: 11/17/2023 fix search entry and button
//        searchTxt = (EditText) findViewById(R.id.search_Home_EditText);
//        searchBtn = (ImageView) findViewById(R.id.searchBtn_Home_Button);

        calendar = (LinearLayout) findViewById(R.id.layoutCalendar);
        events = (LinearLayout) findViewById(R.id.layoutEvents);
        chat = (LinearLayout) findViewById(R.id.layoutChat);
        account = (LinearLayout) findViewById(R.id.layoutPersonal);
        DB = new DBHelper(this);


        
        // TODO: 11/12/2023 Search bar text needs to be checked with DB if DNE. 
        //                      If doesn't Toast saying it doesn't if it does return event   


    }

    
    public void sendToCalendar(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        String name = getIntent().getStringExtra("USERNAME");
        intent.putExtra("USERNAME",name);
        startActivity(intent);
    }

    // TODO: 11/12/2023 fix pathing sendToEvents (onCreation needs to query DB for All of the users Events created (or included in)  
    public void sendToEvents(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    // TODO: 11/12/2023 fix pathing sendToChat (Don't know, no input yet. maybe similar to above)
    public void sendToChat(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void sendToAccount(View view) {
        //Get data from first intent
        String userName = getIntent().getStringExtra("USERNAME");
        //Start New Intent and pass data
        Intent i = new Intent(this, UserProfileActivity.class);
        i.putExtra("USERNAME", userName);
        startActivity(i);
    }
}