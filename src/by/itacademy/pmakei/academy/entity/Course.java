package by.itacademy.pmakei.academy.entity;

import java.io.Serializable;
import java.util.Objects;

public class Course implements Serializable {

  String courseName;
  Teacher teacher;

  public Course(String courseName) {
    this.courseName = courseName;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public String getCourseName() {
    return courseName;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Course course = (Course) o;
    return Objects.equals(courseName, course.courseName) && Objects.equals(teacher, course.teacher);
  }

  @Override
  public int hashCode() {
    return Objects.hash(courseName, teacher);
  }

  @Override
  public String toString() {
    if (teacher != null) {
      return "Курс "
          + courseName
          + ", преподаватель= "
          + teacher.getName()
          + " "
          + teacher.getSurname();
    } else {
      return "Курс " + courseName + ", преподаватель не назначен";
    }
  }

}
