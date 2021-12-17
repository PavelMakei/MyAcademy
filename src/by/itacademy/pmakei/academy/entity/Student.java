package by.itacademy.pmakei.academy.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student extends Human {

    private List<Course> courses = new ArrayList<>();
    private List<Mark> marks = new ArrayList<>();
    //TODO обработка/регистрация законченных курсов?

    public Student(int humanId, String name, String sureName, int age) {
        super(humanId, name, sureName, age);
    }

    public void addCourse(Course c) {
        courses.add(c);
    }

    public void setMark(Mark mark) {
        marks.add(mark);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }


}
