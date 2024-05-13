package com.example.finalact;

import static java.lang.Integer.parseInt;

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
import android.widget.Toast;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {
    EditText etUnameSI, etPwdSI;
    Button btnTecSI, btnStuSI;
    DbManager db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = new DbManager(this);

        etUnameSI = findViewById(R.id.ETunameSI);
        etPwdSI = findViewById(R.id.ETpwdSI);
        btnStuSI = findViewById(R.id.BTNstuSI);
        btnTecSI = findViewById(R.id.BTNtecSI);

        listeners();
    }

    private void listeners() {
        btnStuSI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etUnameSI.getText().toString().equals("") || !etPwdSI.getText().toString().equals("")) {
                    String uname = etUnameSI.getText().toString();
                    String pwd = etPwdSI.getText().toString();

                    try {
                        db.addRow(uname, pwd, 0);

                        Intent intent = new Intent(SignupActivity.this, StudentLoginActivity.class);
                        Toast.makeText(SignupActivity.this, "Successfully Signed Up", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } catch (Exception e) {
                        Log.e("DB Add error", e.toString());
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(SignupActivity.this, "Don't leave the fields blank", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}