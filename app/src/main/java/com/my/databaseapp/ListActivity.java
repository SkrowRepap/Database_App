package com.my.databaseapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    Button btn_close;
    MyAdapter myAdapter;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.lv_list);

        db = new DatabaseHelper(getApplicationContext());

        List<StudentModel> list = db.studentList();

        myAdapter = new MyAdapter(this, list);

        myAdapter.notifyDataSetChanged();
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ViewList.class);
                int studentID = list.get(position).getID();
                i.putExtra("ID", studentID);
                startActivity(i);
            }
        });

    }

    class MyAdapter extends ArrayAdapter<StudentModel> {

        Context context;

        List<StudentModel> sm;


        public MyAdapter(Context c, List<StudentModel> sm) {
            super(c, R.layout.list_item_layout, R.id.mtrl_list_item_text, sm);
            this.sm = sm;


        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View list = layoutInflater.inflate(R.layout.list_item_layout, parent, false);
            ImageView iv = list.findViewById(R.id.mtrl_list_item_icon);
            TextView tv = list.findViewById(R.id.mtrl_list_item_text);
            TextView tv2 = list.findViewById(R.id.mtrl_list_item_secondary_text);
            TextView tv3 = list.findViewById(R.id.mtrl_list_item_tertiary_text);

            String gender = sm.get(position).getGender();
            if (gender.equals("Male")) {
                iv.setImageResource(R.drawable.education__193_);
            } else if (gender.equals("Female")) {
                iv.setImageResource(R.drawable.education__192_);
            }


            tv.setText(sm.get(position).getName());
            tv2.setText(sm.get(position).getCourse_and_year());
            if(sm.get(position).isActive()) {
                tv3.setText("Active");
                tv3.setTextColor(getResources().getColor(R.color.teal_700));
            } else {
                tv3.setText("Inactive");
                tv3.setTextColor(getResources().getColor(R.color.sbmtBTN_color));
            }

            return list;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        List<StudentModel> list = db.studentList();

        myAdapter = new MyAdapter(this, list);

        myAdapter.notifyDataSetChanged();
        listView.setAdapter(myAdapter);

    }


}

