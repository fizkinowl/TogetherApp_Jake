package com.example.togethercalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class UserRegistrationActivity extends AppCompatActivity {

    EditText username,email,mobile,password,rePassword;
    Button signUp,signIn;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);


        username = (EditText) findViewById(R.id.username_TextView);
        email = (EditText) findViewById(R.id.email_TextView);
        mobile = (EditText)findViewById(R.id.mobile_TextView);
        password = (EditText) findViewById(R.id.password_EditText);
        rePassword = (EditText) findViewById(R.id.rePassword_TextView);
        signUp = (Button) findViewById(R.id.signUp_Button);
        signIn = (Button) findViewById(R.id.signIn_Button);
        DB = new DBHelper(this);


        // TODO: 11/10/2023 Add in validation for (Email,mobile#)
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = rePassword.getText().toString();

                if (user.equals("")||pass.equals("")||repass.equals(""))
                    Toast.makeText(UserRegistrationActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        Boolean checkuser = DB.checkusername(user);
                        if(checkuser==false){
                            Boolean insert = DB.insertData(user,pass);
                            if (insert == true){
                                Toast.makeText( UserRegistrationActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                // TODO: 11/10/2023 fix intent (curr set to HomeActivity) (where to send to)
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            }else{

                                Toast.makeText(UserRegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(UserRegistrationActivity.this, "User already exists! Please Sign in",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(UserRegistrationActivity.this, "Password doesn't match",Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),UserLoginActivity.class);
                startActivity(intent);

            }
        });
    }
}