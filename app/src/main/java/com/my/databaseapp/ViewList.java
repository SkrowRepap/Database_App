package com.my.databaseapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.switchmaterial.SwitchMaterial;


public class ViewList extends AppCompatActivity {

    TextView name, age, gender, status, courseYear;
    Button edit, delete,close;
    ImageView iv;
    EditText et_name, et_age, et_gender, et_courseYear;
    SwitchMaterial sv;
    View customView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        name = findViewById(R.id.tv_name);
        age = findViewById(R.id.tv_age);
        gender = findViewById(R.id.tv_gender);
        status = findViewById(R.id.tv_isActive);
        courseYear = findViewById(R.id.tv_courseYear);
        edit = findViewById(R.id.btn_edit);
        delete = findViewById(R.id.btn_delete);
        iv = findViewById(R.id.iv_studentImage);

        //GETTING DATA
        Intent data = getIntent();
        int studentID = data.getIntExtra("ID", -1);
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        StudentModel sm = db.student(studentID);

        if (sm.getGender().equals("Male")) {
            iv.setImageResource(R.drawable.education__193_);
        } else {
            iv.setImageResource(R.drawable.education__192_);
        }
        name.setText(sm.getName());
        age.setText(String.valueOf(sm.getAge()));
        gender.setText(sm.getGender());
        status.setText(sm.isActive() ? "Active" : "Inactive");
        courseYear.setText(sm.getCourse_and_year());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MaterialAlertDialogBuilder(ViewList.this)
                        .setTitle("Confirm delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(!db.deleteStudent(studentID)) {
                                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Succesfully deleted", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();

            }
        });

        edit.setOnClickListener(v -> {
            customView = LayoutInflater.from(ViewList.this).inflate(R.layout.edit_values, null, false);
            et_name = (EditText) customView.findViewById(R.id.et_name1);
            et_age = (EditText) customView.findViewById(R.id.et_age1);
            et_courseYear = (EditText) customView.findViewById(R.id.et_course_and_year1);
            et_gender = (EditText) customView.findViewById(R.id.et_gender1);
            sv = (SwitchMaterial) customView.findViewById(R.id.switch1);

            et_name.setText(sm.getName());
            et_age.setText(String.valueOf(sm.getAge()));
            et_courseYear.setText(sm.getCourse_and_year());
            et_gender.setText(sm.getGender());


            sv.setChecked(sm.isActive());
            sv.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    sv.setText("Active");
                    sv.setTextColor(getResources().getColor(R.color.teal_700));

                } else {
                    sv.setText("Inactive");
                    sv.setTextColor(getResources().getColor(R.color.sbmtBTN_color));
                }
            });


            new MaterialAlertDialogBuilder(ViewList.this).setView(customView)
                    .setTitle("Edit Details")
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d("UPDATE", et_name.getText().toString());
                            StudentModel studentModel = new StudentModel(sm.getID(),
                                    et_name.getText().toString(),
                                    et_gender.getText().toString(),
                                    et_courseYear.getText().toString(),
                                    Integer.parseInt(et_age.getText().toString()),
                                    sv.isChecked() );
                            if(db.updateStudent(studentModel)) {
                                Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();

        });
    }




}