package com.my.databaseapp;

public class StudentModel {
    private int ID;
    private String name;
    private String gender;
    private String course_and_year;
    private int age;
    private boolean isActive;

    public StudentModel(int id, String name, String gender, String course_and_year, int age, boolean isActive) {
        ID = id;
        this.name = name;
        this.gender = gender;
        this.course_and_year = course_and_year;
        this.age = age;
        this.isActive = isActive;
    }

    public StudentModel(String name, String gender, String course_and_year, int age, boolean isActive) {
        this.name = name;
        this.gender = gender;
        this.course_and_year = course_and_year;
        this.age = age;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCourse_and_year() {
        return course_and_year;
    }

    public void setCourse_and_year(String course_and_year) {
        this.course_and_year = course_and_year;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
