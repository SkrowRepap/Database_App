package com.my.databaseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String STUDENT_TABLE = "STUDENT_TABLE";
    public static final String ID = "ID";
    public static final String STUDENT_NAME = "STUDENT_NAME";
    public static final String STUDENT_AGE = "STUDENT_AGE";
    public static final String STUDENT_GENDER = "STUDENT_GENDER";
    public static final String STUDENT_COURSE_AND_YEAR = "STUDENT_COURSE_AND_YEAR";
    public static final String STUDENT_ACTIVE = "STUDENT_ACTIVE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "student.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + STUDENT_TABLE + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + STUDENT_NAME + " TEXT, " + STUDENT_AGE + " INTEGER, " + STUDENT_GENDER + " TEXT, " + STUDENT_COURSE_AND_YEAR + " TEXT, " + STUDENT_ACTIVE + " BOOL) ";
        db.execSQL(createTableStatement);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addStudent (StudentModel sm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STUDENT_NAME, sm.getName());
        cv.put(STUDENT_AGE, sm.getAge());
        cv.put(STUDENT_GENDER, sm.getGender());
        cv.put(STUDENT_COURSE_AND_YEAR, sm.getCourse_and_year());
        cv.put(STUDENT_ACTIVE, sm.isActive());

        long insert = db.insert(STUDENT_TABLE, null, cv);
        db.close();
        return insert != -1;

    }

    public List <StudentModel> studentList() {
        List<StudentModel> list = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " + STUDENT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery,null);

        if (cursor.moveToFirst()) {
            do {
                int studentID = cursor.getInt(0);
                String studentName = cursor.getString(1);
                int studentAge = cursor.getInt(2);
                String studentGender = cursor.getString(3);
                String studentCourseYear = cursor.getString(4);
                boolean isActive = cursor.getInt(5) == 1;
                StudentModel studentModel = new StudentModel(studentID,studentName,studentGender,studentCourseYear,studentAge,isActive);
                list.add(studentModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return list;
    }

    public StudentModel student(int studentID) {
        StudentModel studentModel = null;
        String sqlQuery = "SELECT * FROM " + STUDENT_TABLE + " WHERE ID=" + studentID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery,null);

        if (cursor.moveToFirst()) {
            do {
                String studentName = cursor.getString(1);
                int studentAge = cursor.getInt(2);
                String studentGender = cursor.getString(3);
                String studentCourseYear = cursor.getString(4);
                boolean isActive = cursor.getInt(5) == 1;
                studentModel = new StudentModel(cursor.getInt(0),studentName,studentGender,studentCourseYear,studentAge,isActive);

            } while (cursor.moveToNext());
        }
    db.close();
        cursor.close();

        return studentModel;
    }
    public boolean deleteStudent(int studentID) {

        SQLiteDatabase db = this.getWritableDatabase();
        long deleted = db.delete(STUDENT_TABLE,ID + "=" + studentID, null);
        db.close();
        return deleted > 0;
    }

    public boolean updateStudent(StudentModel sm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STUDENT_NAME, sm.getName());
        cv.put(STUDENT_AGE, sm.getAge());
        cv.put(STUDENT_GENDER, sm.getGender());
        cv.put(STUDENT_COURSE_AND_YEAR, sm.getCourse_and_year());
        cv.put(STUDENT_ACTIVE, sm.isActive());

        long insert = db.update(STUDENT_TABLE, cv, ID + " = " + sm.getID(), null);

        db.close();
        return insert > 0;
    }
}
