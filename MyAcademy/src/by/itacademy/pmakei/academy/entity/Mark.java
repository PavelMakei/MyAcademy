package by.itacademy.pmakei.academy.entity;

import java.util.Objects;

public class Mark implements Comparable{

    private int value;
    private Course course;
    private Teacher teacher;
    private String feedback;
    //private AllowedMark value2;

    public Mark(Teacher teacher, Course course, int i, String feedback) {
        this.course = course;
        this.value = i;
        this.teacher = teacher;
        this.feedback = feedback;


    }

    public Course getCourse() {
        return course;
    }

    public int getValue() {
        return value;
    }

    public String getFeedback() {
        return feedback;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mark mark = (Mark) o;
        return value == mark.value && Objects.equals(course, mark.course) && Objects.equals(teacher, mark.teacher) && Objects.equals(feedback, mark.feedback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, course.getCourseName(), teacher.getPersonalId(),feedback);
    }


    @Override
    public int compareTo(Object o) {
        int result = this.value - ((Mark) o).value;
        return result;
    }
}
