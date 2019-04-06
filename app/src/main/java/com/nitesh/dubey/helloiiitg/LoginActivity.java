package com.nitesh.dubey.helloiiitg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void studentRegister (View view) {
        Intent intent = new Intent(LoginActivity.this, StudentRegister.class);
        startActivity(intent);
    }

    public void instructorLogin (View view) {
        Intent intent = new Intent(LoginActivity.this, InstructorLogin.class);
        startActivity(intent);
    }
}
