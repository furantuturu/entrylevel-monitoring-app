package com.example.finalact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

public class SubjectSelectionActivity extends AppCompatActivity {
    EditText etEnterSubject;
    Button btnAddClass;
    ListView lvSubjectSelect;
    ArrayList<String> subjects;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_selection);

        etEnterSubject = findViewById(R.id.ETentersubject);
        btnAddClass = findViewById(R.id.BTNaddSubject);
        lvSubjectSelect = findViewById(R.id.LVsubjectSelect);

        subjects = new ArrayList<>();

        subjects.add("Mobile Programming");

        adapter = new ArrayAdapter<>(this, R.layout.activity_subject_list_item, R.id.subject, subjects);
        adapter.notifyDataSetChanged();

        lvSubjectSelect.setAdapter(adapter);

        viewListeners();
    }

    private void viewListeners() {
        lvSubjectSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pos = (String) lvSubjectSelect.getItemAtPosition(position);
                if (pos == "Mobile Programming") {
                    Intent intent = new Intent(SubjectSelectionActivity.this, QRCodeActivity.class);
                    startActivity(intent);
                }
            }
        });
//        btnAddClass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String item = etEnterSubject.getText().toString();
//
//                if (!item.isEmpty()) {
//                    subjects.add(item);
//
//                    adapter.notifyDataSetChanged();
//                } else {
//                    Toast.makeText(ClassSelectionActivity.this, "No such Subject as blank", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
}