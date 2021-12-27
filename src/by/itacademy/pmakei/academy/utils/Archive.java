package by.itacademy.pmakei.academy.utils;

import java.io.Serializable;
import java.util.List;

/**
 * @author Pavel Makei
 */
public class Archive implements Serializable {
    private List students;
    private List Teachers;
    private List Courses;
    private int humanIdCount;

    public List getStudents() {
        return students;
    }

    public void setStudents(List students) {
        this.students = students;
    }

    public List getTeachers() {
        return Teachers;
    }

    public void setTeachers(List teachers) {
        Teachers = teachers;
    }

    public List getCourses() {
        return Courses;
    }

    public void setCourses(List courses) {
        Courses = courses;
    }

    public int getHumanIdCount() {
        return humanIdCount;
    }

    public void setHumanIdCount(int humanIdCount) {
        this.humanIdCount = humanIdCount;
    }

}
