package com.example.memory_gameca;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class successful_signup extends AppCompatActivity {
    TextView mInfoTxt;
    Button mLoginAgain;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_signup);

        mInfoTxt = findViewById(R.id.txtInfo);
        mLoginAgain = findViewById(R.id.btnLoginAgain);

        pref = getSharedPreferences(
                "user_credentials", MODE_PRIVATE);
        //username is placed INTO shared preferences
        String username = pref.getString("username", "");

        //text is displayed
        mInfoTxt.setText("Hi, " + username +
                "! Successful sign up!");

        //when user clicks btn, go to Login page
        //info will be cleared from SHARED PREFERENCES
        mLoginAgain.setOnClickListener(v -> {
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();

            gotoLogin();
        });
    }

    private void gotoLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}