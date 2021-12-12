package by.itacademy.pmakei.academy.entity;

import java.util.ArrayList;

public class Student extends Human {

    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<Mark> marks = new ArrayList<>();


    public Student(int humanId, String name, String sureName, int age) {
        super(humanId, name, sureName, age);
    }

    public void addCourse(Course c) {
        courses.add(c);
    }

    public void setMark(Mark e) {
        marks.add(e);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public ArrayList<Mark> getMarks() {
        return marks;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }


}
