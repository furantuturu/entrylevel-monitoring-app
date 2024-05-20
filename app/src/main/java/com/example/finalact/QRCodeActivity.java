package com.example.finalact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QRCodeActivity extends AppCompatActivity {
    Button btnMonitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        btnMonitor = findViewById(R.id.BTNmonitor);

        listeners();
    }

    private void listeners() {
        btnMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QRCodeActivity.this, StudentPresenceList.class);
                startActivity(intent);
            }
        });
    }

}