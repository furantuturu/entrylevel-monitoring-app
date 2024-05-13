package com.example.finalact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StudentSignUpActivity extends AppCompatActivity {

    EditText username,password;
    Button btn;
    DbManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);

        db = new DbManager(this);

        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);
        btn = findViewById(R.id.btnSignUp);

        buttonListener();
    }

    private void buttonListener() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!username.getText().toString().equals("") || !password.getText().toString().equals("")) {
                    String uname = username.getText().toString();
                    String pwd = password.getText().toString();

                    try {
                        db.addRow(uname, pwd, 0);

                        Intent intent = new Intent(StudentSignUpActivity.this, StudentLoginActivity.class);
                        Toast.makeText(StudentSignUpActivity.this, "Successfully Signed Up", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } catch (Exception e) {
                        Log.e("DB Add error", e.toString());
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(StudentSignUpActivity.this, "Don't leave the fields blank", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}