package com.example.togethercalendar;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    TextView username;
    EditText searchTxt;
    ImageView search;
    LinearLayout calendar,events,chat,account;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String NAME = getIntent().getStringExtra("USERNAME");

        //bottom navigation bar toggle method to get to different UI pages
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menuHome);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menuHome) {
                return true;
            } else if (id == R.id.menuCalendar) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("USERNAME",NAME));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (id == R.id.menuProfile) {
                startActivity(new Intent(getApplicationContext(), UserProfileActivity.class).putExtra("USERNAME",NAME));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (id == R.id.menuSettings) {
                startActivity(new Intent(getApplicationContext(), UserProfileActivity.class).putExtra("USERNAME",NAME));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else {
                return false;
            }
        });

        //Change textUserName to logged in usersName
        String name = getIntent().getStringExtra("USERNAME");
        ((TextView)findViewById(R.id.textUsername)).setText(name);
        username = (TextView) findViewById(R.id.textUsername);
        searchTxt = (EditText) findViewById(R.id.search_Home_EditText);
        search = (ImageView) findViewById(R.id.searchBtn_Home_Button);
        calendar = (LinearLayout) findViewById(R.id.layoutCalendar);
        events = (LinearLayout) findViewById(R.id.layoutEvents);
        chat = (LinearLayout) findViewById(R.id.layoutChat);
        account = (LinearLayout) findViewById(R.id.layoutPersonal);
        DB = new DBHelper(this);
    }

    
    public void sendToCalendar(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        String name = getIntent().getStringExtra("USERNAME");
        intent.putExtra("USERNAME",name);
        startActivity(intent);
    }

    public void sendToEvents(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        String name = getIntent().getStringExtra("USERNAME");
        intent.putExtra("USERNAME",name);
        startActivity(intent);
    }

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