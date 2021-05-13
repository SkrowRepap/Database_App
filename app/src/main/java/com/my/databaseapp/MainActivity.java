package com.my.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et_name, et_age, et_gender, et_course_and_year;
    Button btn_submit, btn_viewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_gender = findViewById(R.id.et_gender);
        et_course_and_year = findViewById(R.id.et_course_and_year);
        btn_submit = findViewById(R.id.btn_submit);
        btn_viewAll = findViewById(R.id.btn_viewAll);

        btn_submit.setOnClickListener(v -> {
            String name = et_name.getText().toString();
            int age = Integer.parseInt(et_age.getText().toString());
            String gender = et_gender.getText().toString();
            String courseYear = et_course_and_year.getText().toString();

            StudentModel sm = new StudentModel(-1, name,gender,courseYear,age,true);
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());

            if(db.addStudent(sm)) {
                Toast.makeText(getApplicationContext(), "Successfully added", Toast.LENGTH_LONG).show();
            }
            else {
                if(db.addStudent(sm)) {
                    Toast.makeText(getApplicationContext(), "Failed to add", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_viewAll.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(i);

        });
    }
}