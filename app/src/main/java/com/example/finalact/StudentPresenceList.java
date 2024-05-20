package com.example.finalact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentPresenceList extends AppCompatActivity {
    ListView lvStudentPresent;
    ArrayList<String> studentlist = new ArrayList<>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_presence_list);

        lvStudentPresent = findViewById(R.id.LVstudentpresent);

        adapter = new ArrayAdapter<>(this, R.layout.student_list, R.id.studentStyle, studentlist);
        lvStudentPresent.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("students");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentlist.clear();
                for (DataSnapshot ss : snapshot.getChildren()) {
                    Students studs = ss.getValue(Students.class);
                    String studentDetail = "Name: " + studs.getName() + "\n" + "Email: " + studs.getEmail() + "\n" + "Present at: " + studs.getDatetime();
                    studentlist.add(studentDetail);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase Error", error.toString());

            }
        });
    }



}