package com.example.togethercalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UserLoginActivity extends AppCompatActivity {

    EditText username, password;
    TextView signUp;
    Button btnlogin;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        username = findViewById(R.id.username_login_textView);
        password = findViewById(R.id.password_login_TextView);
        btnlogin = findViewById(R.id.login_Button);
        signUp = findViewById(R.id.signUp_TextView);
        DB = new DBHelper(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("")||pass.equals("")) {
                    Toast.makeText(UserLoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Retrieve hashed password from the database
                    String storedHashedPassword = DB.getStoredPassword(user);

                    if (storedHashedPassword != null) {
                        // Verify entered password against stored hashed password
                        boolean isPasswordCorrect = BCrypt.verifyer().verify(pass.toCharArray(), storedHashedPassword).verified;

                        if (isPasswordCorrect) {
                            Toast.makeText(UserLoginActivity.this, "Signed in successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            //used to pass userName data to HomeActivity
                            intent.putExtra("USERNAME",user);
                            startActivity(intent);
                        } else {
                            Toast.makeText(UserLoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UserLoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),UserRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}