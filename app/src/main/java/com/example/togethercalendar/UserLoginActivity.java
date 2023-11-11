package com.example.togethercalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserLoginActivity extends AppCompatActivity {

    EditText username, password;
    TextView signUp;
    Button btnlogin;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        username = (EditText) findViewById(R.id.username_login_textView);
        password = (EditText) findViewById(R.id.password_login_TextView);
        btnlogin = (Button) findViewById(R.id.login_Button);
        signUp = (TextView) findViewById(R.id.signUp_TextView);
        DB = new DBHelper(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals(""))
                    Toast.makeText(UserLoginActivity.this, "PLease enter all the fields",Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkUserPass = DB.checkusernamepassword(user,pass);
                    if(checkUserPass==true){
                        Toast.makeText(UserLoginActivity.this, "Signed in successfully", Toast.LENGTH_SHORT).show();
                        // TODO: 11/10/2023 fix intent (curr set to HomeActivity) (where to send to)
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(UserLoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

        // TODO: 11/10/2023  maybe don't need anymore (comment-Out if register works)
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),UserRegistrationActivity.class);
                startActivity(intent);

            }
        });


    };


    public void register(View view) {
        Intent intent = new Intent(this, UserRegistrationActivity.class);
        startActivity(intent);
    }


}