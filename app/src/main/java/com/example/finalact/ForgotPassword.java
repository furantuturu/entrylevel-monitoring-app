package com.example.finalact;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ForgotPassword extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        imageView = findViewById(R.id.imageViews);

        Animation fadein = AnimationUtils.loadAnimation(this, R.anim.fadein);

        imageView.startAnimation(fadein);
    }
}