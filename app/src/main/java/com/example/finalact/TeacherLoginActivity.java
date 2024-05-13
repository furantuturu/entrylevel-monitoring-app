package com.example.finalact;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class TeacherLoginActivity extends AppCompatActivity {
    EditText etTecName, etTecPwd;
    Button btnTecLogin, btnTecSignup;
    TextView fgtPwdTec;
    ImageView imageView;
    Intent intent;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        etTecName = findViewById(R.id.ETtecname);
        etTecPwd = findViewById(R.id.ETtecpwd);
        btnTecLogin = findViewById(R.id.BTNteclogin);
        btnTecSignup = findViewById(R.id.BTNtecsignup);
        fgtPwdTec = findViewById(R.id.forgotPasswordTec);
        imageView = findViewById(R.id.teacherImg);

        Animation fadein = AnimationUtils.loadAnimation(this, R.anim.fadein);
        imageView.startAnimation(fadein);



        listeners();
    }
    public void listeners(){
        fgtPwdTec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(TeacherLoginActivity.this, ForgotPasswordTeacher.class);
                startActivity(intent);

            }
        });

        btnTecLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnTecSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(TeacherLoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}