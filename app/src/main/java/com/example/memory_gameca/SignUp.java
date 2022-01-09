package com.example.memory_gameca;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    EditText mUsernameTxt, mPasswordTxt, mConfirmPasswordTxt;
    Button mSignupBtn;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = new DatabaseHelper(this);
        mUsernameTxt = findViewById(R.id.txtUsername);
        mPasswordTxt = findViewById(R.id.txtPassword);
        mConfirmPasswordTxt = findViewById(R.id.txtConfirmPassword);
        mSignupBtn = findViewById(R.id.btnSignUp);

        //trying to see if there is any data from SHAREDPREFERENCES
        SharedPreferences pref = getSharedPreferences(
                "user_credentials", MODE_PRIVATE);
        if (pref.contains("username") && pref.contains("password")) {
            Toast.makeText(getApplicationContext(),
                    "Account exists! Please Login.",
                    Toast.LENGTH_SHORT).show();
        }

        mSignupBtn.setOnClickListener(v -> {
            String username = mUsernameTxt.getText().toString();
            String password = mPasswordTxt.getText().toString();
            String cpassword = mConfirmPasswordTxt.getText().toString();

            //check for empty fields
            if (checkNullField()==false && password.equals(cpassword)) {
                    Boolean checkusername = db.CheckUsername(username);
                    if (checkusername) {
                        Boolean insert = db.Insert(username, password);
                        if (insert) {
                            //insert to sharedpreferences
                            SharedPreferences.Editor editor = pref.edit();
                            //save any changes by calling editor
                            editor.putString("username", username);
                            editor.putString("password", password);
                            //commit to confirm
                            editor.commit();
                            //set empty fields
                            mUsernameTxt.setText("");
                            mPasswordTxt.setText("");
                            mConfirmPasswordTxt.setText("");
                            gotoSuccessfulSignup();
                        }
                    }
                } else {
                    //toast msg will appear
                    Toast.makeText(getApplicationContext(),
                            "Credentials are not valid, check again.",
                            Toast.LENGTH_SHORT).show();
                }
            });
//            else {
//                Toast.makeText(this,
//                        "No empty fields please!",
//                        Toast.LENGTH_SHORT).show();
//            }
    }

    private void gotoSuccessfulSignup() {
        Intent intent = new Intent(this, successful_signup.class);
        startActivity(intent);
    }

    //additional feature
    boolean isEmpty(@NonNull EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean checkNullField() {
        if (isEmpty(mUsernameTxt)) {
//            Toast t = Toast.makeText(this, "Please enter username!",
//                    Toast.LENGTH_SHORT);
//            t.show();
            return true;
        }
        if (isEmpty(mPasswordTxt)) {
//            Toast t = Toast.makeText(this, "Please enter password!",
//                    Toast.LENGTH_SHORT);
//            t.show();
            return true;
        }
        if (isEmpty(mConfirmPasswordTxt)) {
//            Toast t = Toast.makeText(this, "Please enter valid password!",
//                    Toast.LENGTH_SHORT);
//            t.show();
            return true;
        }
        return false;
    }

}