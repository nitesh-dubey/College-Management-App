package com.nitesh.dubey.helloiiitg;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText emailStudent,passwordStudent;
    TextView login;
    String usremail, usrpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailStudent = findViewById(R.id.emailStudent);
        passwordStudent = findViewById(R.id.passwordStudent);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usremail = emailStudent.getText().toString();
                usrpassword = passwordStudent.getText().toString();
                signIn();
            }
        });
    }

    public void studentRegister (View view) {
        Intent intent = new Intent(LoginActivity.this, StudentRegister.class);
        startActivity(intent);
    }

    public void instructorLogin (View view) {
        Intent intent = new Intent(LoginActivity.this, InstructorLogin.class);
        startActivity(intent);
    }




    private void signIn() {

        if(usremail.isEmpty()) {
            emailStudent.setError("Email is required");
            emailStudent.requestFocus();
            return;
        }
        if(usrpassword.isEmpty()) {
            passwordStudent.setError("Password is required");
            passwordStudent.requestFocus();
            return;
        }

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(usremail,usrpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Login Unsuccessful",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
