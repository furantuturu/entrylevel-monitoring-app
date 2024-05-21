package com.example.finalact;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class StudentLoginActivity extends AppCompatActivity {
    EditText etStuEmail, etStuPwd;
    Button btnStuSignup, btnStuLogin;
    TextView fgtPwdTec;
    ImageView imgView;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);


        etStuEmail = findViewById(R.id.ETstuemail);
        etStuPwd = findViewById(R.id.ETstupwd);
        btnStuLogin = findViewById(R.id.BTNstulogin);
        btnStuSignup = findViewById(R.id.BTNstusignup);
        fgtPwdTec = findViewById(R.id.forgot_pass_stu);

        imgView = findViewById(R.id.kidImage);

        Animation fadein = AnimationUtils.loadAnimation(this, R.anim.fadein);
        imgView.startAnimation(fadein);

        auth = FirebaseAuth.getInstance();

        listeners();
    }

    private void listeners() {
        fgtPwdTec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentLoginActivity.this , ForgotPasswordStudent.class);
                startActivity(intent);
            }
        });


        btnStuLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etStuEmail.getText().toString().isEmpty() || etStuPwd.getText().toString().isEmpty()){
                    Toast.makeText(StudentLoginActivity.this, "Please fill the fields!" , Toast.LENGTH_SHORT).show();
                }else {
                    String mail = etStuEmail.getText().toString();
                    String pwd = etStuPwd.getText().toString();

                    Bundle bundle = new Bundle();
                    bundle.putString("EMAIL", mail);

                    loginUser(mail, pwd, bundle);
                }
            }
        });

        btnStuSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentLoginActivity.this, StudentSignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String mail, String pwd, Bundle bundle) {
        auth.signInWithEmailAndPassword(mail, pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(StudentLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(StudentLoginActivity.this, QRScanActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

}