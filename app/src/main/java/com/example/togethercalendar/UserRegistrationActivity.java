package com.example.togethercalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UserRegistrationActivity extends AppCompatActivity {

    EditText username,emailUser,mobileUser,password,rePassword;
    Button signUp,signIn;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        username = (EditText) findViewById(R.id.username_prof_EditText);
        emailUser = (EditText) findViewById(R.id.email_prof_EditText);
        mobileUser = (EditText) findViewById(R.id.mobile_prof_EditText);
        password = (EditText) findViewById(R.id.password_EditText);
        rePassword = (EditText) findViewById(R.id.rePassword_TextView);
        signUp = (Button) findViewById(R.id.signUp_Button);
        signIn = (Button) findViewById(R.id.signIn_Button);
        DB = new DBHelper(this);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String email = emailUser.getText().toString();
                String mobile = mobileUser.getText().toString();
                String pass = password.getText().toString();
                String repass = rePassword.getText().toString();

                if (user.equals("") || pass.equals("") || repass.equals("") || email.equals("") || mobile.equals(""))
                    Toast.makeText(UserRegistrationActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                else {
                    // Validate username
                    if (isValidUsername(user)) {
                        if(isValidPassword(pass)) {
                            // Validate email
                            if (isValidEmail(email)) {
                                // Validate mobile number
                                if (isValidMobileNumber(mobile)) {
                                    if (pass.equals(repass)) {
                                        Boolean checkUsername = DB.checkUsername(user);
                                        Boolean checkEmail = DB.checkEmail(email);
                                        Boolean checkMobile = DB.checkMobile(mobile);

                                        if (!checkUsername && !checkEmail && !checkMobile) {

                                            // Hash the password using BCrypt
                                            String hashedPassword = BCrypt.withDefaults().hashToString(12, pass.toCharArray());

                                            // Insert the hashed password, email, and mobile into the database
                                            Boolean insert = DB.insertUserData(user, hashedPassword, email, mobile);

                                            if (insert) {
                                                Toast.makeText(UserRegistrationActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                                //Pass username data with intent
                                                intent.putExtra("USERNAME",user);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(UserRegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            if (checkUsername) {
                                                Toast.makeText(UserRegistrationActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                                            } else if (checkEmail) {
                                                Toast.makeText(UserRegistrationActivity.this, "Email already taken", Toast.LENGTH_SHORT).show();
                                            } else if (checkMobile) {
                                                Toast.makeText(UserRegistrationActivity.this, "Mobile number already taken", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } else {
                                        Toast.makeText(UserRegistrationActivity.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(UserRegistrationActivity.this, "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(UserRegistrationActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(UserRegistrationActivity.this, "Password must contain at least 6 characters,1 number and 1 special character", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UserRegistrationActivity.this, "Username must be at least 4 characters", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
                startActivity(intent);
            }
        });
    }


    // Method to validate the username
    private boolean isValidUsername(String username) {
        // Check if the username is at least 4 characters long and contains only alphanumeric characters
        return username.length() >= 4 && username.matches("^[a-zA-Z0-9]+$");
    }

    // Method to validate the password
    private boolean isValidPassword(String password){
        return password.matches("^(?=.*[A-Za-z0-9])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z0-9\\d@$!%*?&]{6,}$");
    }

    // Method to validate the email
    private boolean isValidEmail(String email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Function to validate the mobile number
    private boolean isValidMobileNumber(String mobile) {
         //RegEx to check mobile number has valid format of 10 digits
        return mobile.matches("\\d{10}");
    }
}

