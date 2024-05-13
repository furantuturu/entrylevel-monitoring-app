package com.example.finalact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StudentLoginActivity extends AppCompatActivity {
    EditText etStuName, etStuPwd;
    Button btnStuSignup, btnStuLogin;
    TextView fgtPwdTec;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        etStuName = findViewById(R.id.ETstuname);
        etStuPwd = findViewById(R.id.ETstupwd);
        btnStuLogin = findViewById(R.id.BTNstulogin);
        btnStuSignup = findViewById(R.id.BTNstusignup);
        fgtPwdTec = findViewById(R.id.forgot_pass_stu);

        listeners();
    }

    private void listeners() {
        fgtPwdTec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(StudentLoginActivity.this , ForgotPasswordStudent.class);
                startActivity(intent);
            }
        });

        btnStuLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etStuName.getText().toString().equals("") || !etStuPwd.getText().toString().equals("")) {

                } else {
                    Toast.makeText(StudentLoginActivity.this, "Don't leave the fields blank", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnStuSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(StudentLoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }


}