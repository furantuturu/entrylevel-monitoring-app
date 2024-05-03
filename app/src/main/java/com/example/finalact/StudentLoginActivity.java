package com.example.finalact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StudentLoginActivity extends AppCompatActivity {

    TextView txtView;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        txtView = findViewById(R.id.forgot_pass);


        addTxtListener();
    }

    private void addTxtListener() {
        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(StudentLoginActivity.this , ForgotPassword.class);
                startActivity(intent);

            }
        });
    }


}