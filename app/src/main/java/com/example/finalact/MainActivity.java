package com.example.finalact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn2;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn2 = findViewById(R.id.button2);

        addButtonListener();
    }

    private void addButtonListener() {
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(MainActivity.this, StudentLoginActivity.class);
                startActivity(intent);

            }
        });
    }
}