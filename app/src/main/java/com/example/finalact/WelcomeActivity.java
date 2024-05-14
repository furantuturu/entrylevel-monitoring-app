package com.example.finalact;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {
    TextView studentName;
    Button btnLeaveClass;
    SharedPreferences sharedPreferences;
    public static final String MyPreferences = "MYPREFS";
    String spStuNamePresent;
    private long pressedTime;
    DbManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        db = new DbManager(this);

        studentName = findViewById(R.id.TVstudentnamepresent);
        btnLeaveClass = findViewById(R.id.BTNleaveclass);

        sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        spStuNamePresent = sharedPreferences.getString(StudentLoginActivity.UNAME, null);

        studentName.setText(spStuNamePresent.substring(0, 1).toUpperCase() + spStuNamePresent.substring(1).toLowerCase());

        listeners();
    }

    private void listeners() {
        btnLeaveClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateRow(0);

                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.clear();
                editor.apply();

                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();

            db.updateRow(0);

            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.clear();
            editor.apply();


            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to leave class", Toast.LENGTH_SHORT).show();
        }

        pressedTime = System.currentTimeMillis();
    }

}