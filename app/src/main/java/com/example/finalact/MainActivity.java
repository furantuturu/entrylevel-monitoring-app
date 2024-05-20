package com.example.finalact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.window.SplashScreenView;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button teacherLogin;
    Button studentLogin;
    Button teacherSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        teacherLogin = findViewById(R.id.btnTeacherLogin);
        studentLogin = findViewById(R.id.btnStudentLogin);
        teacherSignUp = findViewById(R.id.btnTeacherSignUp);

        buttonListener();
    }

    private void buttonListener() {
//        teacherLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, SubjectSelectionActivity.class);
//                startActivity(intent);
//            }
//        });

        studentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudentLoginActivity.class);
                startActivity(intent);
            }
        });
        teacherSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this , TeacherLoginActivity.class);
               startActivity(intent);
            }
        });

    }

}