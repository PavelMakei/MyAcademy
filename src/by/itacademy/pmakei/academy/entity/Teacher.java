package by.itacademy.pmakei.academy.entity;

import by.itacademy.pmakei.academy.utils.AcademyUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Teacher extends Human{

    private Course course;

    public Teacher(int id, String name, String sureName, int age) {
        super(id, name, sureName, age);
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setMark(Student student, int markValue, String feedback) {
        if (student.getCourses().contains(course)) {
            student.setMark(new Mark(this, course, markValue, feedback ));
        } else {
            System.out.println("Student doesn't join this course");
        }
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }


}
