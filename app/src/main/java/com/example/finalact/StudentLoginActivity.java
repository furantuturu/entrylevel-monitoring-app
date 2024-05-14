package com.example.finalact;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentLoginActivity extends AppCompatActivity {
    EditText etStuName, etStuPwd;
    Button btnStuSignup, btnStuLogin;
    TextView fgtPwdTec;
    public static final String MyPREFERENCES = "MYPREFS";
    public static final String UNAME = "UNAME_KEY";
    SharedPreferences sharedPreferences;
    DbManager db;
    String spUname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        db = new DbManager(this);

        etStuName = findViewById(R.id.ETstuname);
        etStuPwd = findViewById(R.id.ETstupwd);
        btnStuLogin = findViewById(R.id.BTNstulogin);
        btnStuSignup = findViewById(R.id.BTNstusignup);
        fgtPwdTec = findViewById(R.id.forgot_pass_stu);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        spUname = sharedPreferences.getString("UNAME", null);

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
                if (!etStuName.getText().toString().equals("") && !etStuPwd.getText().toString().equals("")) {
                    String uname = etStuName.getText().toString();
                    String pwd = etStuPwd.getText().toString();

                    try {
                        ArrayList<Object> data = db.getRowAsArray(uname);

                        if (pwd.equals(data.get(2).toString())) {
                            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString(UNAME, data.get(1).toString());
                            editor.apply();

                            Intent intent = new Intent(StudentLoginActivity.this, QRScanActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(StudentLoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Log.e("DB Error", e.toString());
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(StudentLoginActivity.this, "Don't leave the fields blank", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnStuSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentLoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }


}