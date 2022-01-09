package com.example.memory_gameca;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText mUsernameTxt, mPasswordTxt;
    Button mLoginBtn, mSignupBtn;
    DatabaseHelper db;

    //TODO: Consider an edit account page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        mUsernameTxt = findViewById(R.id.txtUsername);
        mPasswordTxt = findViewById(R.id.txtPassword);
        mLoginBtn = findViewById(R.id.btnLogin);
        mSignupBtn = findViewById(R.id.btnSignUp);

        mSignupBtn.setOnClickListener(v -> { gotoSignup();});

           mLoginBtn.setOnClickListener(v -> {
            //upon clicking login, check for null field
            if(checkNullField()==false) {
                String username = mUsernameTxt.getText().toString();
                String password = mPasswordTxt.getText().toString();
                //get username and password from db
                Boolean checklogin = db.CheckLogin(username, password);
                if (checklogin) {
                    Toast.makeText(getApplicationContext(),
                            "Login Successful",
                            Toast.LENGTH_SHORT).show();
                    gotoDownloadPhoto();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Invalid username or password",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this,
                        "No empty fields please!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //additional feature
    boolean isEmpty(@NonNull EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    boolean checkNullField() {
        if(isEmpty(mUsernameTxt)){return true;}
        if(isEmpty(mPasswordTxt)) {return true;}
            return false;
    }

    private void gotoDownloadPhoto() {
        Intent intent = new Intent(this, DownloadPhoto.class);
        startActivity(intent);
    }
    private void gotoSignup() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
