package by.itacademy.pmakei.academy.entity;

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

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

}
