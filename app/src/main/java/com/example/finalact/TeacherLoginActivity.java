package com.example.finalact;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class TeacherLoginActivity extends AppCompatActivity {

    TextView txtView;
    ImageView imageView;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        txtView = findViewById(R.id.forgotPassword);
        imageView = findViewById(R.id.teacherImg);

        Animation fadein = AnimationUtils.loadAnimation(this, R.anim.fadein);
        imageView.startAnimation(fadein);



        addTxtView();
    }
    public void addTxtView(){
        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                intent = new Intent(TeacherLoginActivity.this, ForgotPasswordTeacher.class);
                startActivity(intent);

            }
        });
    }
}