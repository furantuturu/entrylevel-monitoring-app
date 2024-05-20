package com.example.finalact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StudentSignUpActivity extends AppCompatActivity {
    EditText email,password, username;
    Button btn;
    DbManager db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);

        db = new DbManager(this);

        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        btn = findViewById(R.id.btnSignUp);

        auth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String pwd = password.getText().toString();

                if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pwd)) {
                    Toast.makeText(StudentSignUpActivity.this, "Don't leave the fields blank", Toast.LENGTH_SHORT).show();
                }
                else if (pwd.length() < 6) {
                    Toast.makeText(StudentSignUpActivity.this, "Password must be 7 characters long", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(mail, pwd);
                }
            }
        });
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!username.getText().toString().equals("") || !password.getText().toString().equals("")) {
//                    String uname = username.getText().toString();
//                    String pwd = password.getText().toString();
//
//                    try {
//                        db.addRow(uname, pwd, 0);
//
//                        Intent intent = new Intent(StudentSignUpActivity.this, StudentLoginActivity.class);
//                        Toast.makeText(StudentSignUpActivity.this, "Successfully Signed Up", Toast.LENGTH_SHORT).show();
//                        startActivity(intent);
//                    } catch (Exception e) {
//                        Log.e("DB Add error", e.toString());
//                        e.printStackTrace();
//                    }
//                } else {
//                    Toast.makeText(StudentSignUpActivity.this, "Don't leave the fields blank", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
    }

    private void registerUser(String email, String pwd) {
        auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(StudentSignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(StudentSignUpActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StudentSignUpActivity.this, StudentLoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(StudentSignUpActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}