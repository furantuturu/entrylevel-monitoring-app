package com.example.finalact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class WelcomeActivity extends AppCompatActivity {
    TextView studentNameWelcome;
    Button btnLogout;
    Bundle received;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        studentNameWelcome = findViewById(R.id.TVstudentnamepresent);
        btnLogout = findViewById(R.id.BTNlogout);

        studentNameWelcome.setText(studentName());

        Intent intent = new Intent(WelcomeActivity.this, MyService.class);

        Bundle bundle = new Bundle();
        String stuname = received.getString("STUDENTNAME").substring(0, 1).toUpperCase() + received.getString("STUDENTNAME").substring(1).toLowerCase();
        bundle.putString("STUNAME", stuname);

        intent.putExtras(bundle);
        startService(intent);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                FirebaseAuth.getInstance().signOut();

                Toast.makeText(WelcomeActivity.this, "Logged out", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(WelcomeActivity.this, StudentLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        deleteData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        deleteData();
    }

    private String studentName() {
        received = getIntent().getExtras();
        return received.getString("STUDENTNAME").substring(0, 1).toUpperCase() + received.getString("STUDENTNAME").substring(1).toLowerCase();

    }

    public void deleteData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query studentNameQuery = reference.child("students").orderByChild("name").equalTo(studentName());

        studentNameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot studentNameSS : snapshot.getChildren()) {
                    studentNameSS.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("on cancelled", String.valueOf(error.toException()));
            }
        });
    }


}