package com.example.finalact;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;


public class TeacherMainActivity extends AppCompatActivity {
    TextView studentName;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

        studentName = findViewById(R.id.TVstudentname);
        uri = getIntent().getData();

        if (uri != null){
            List<String> parameters = uri.getPathSegments();
            String param = parameters.get(parameters.size() - 1);
            studentName.setText(param);
        }






    }
}