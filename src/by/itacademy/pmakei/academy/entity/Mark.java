package by.itacademy.pmakei.academy.entity;

import by.itacademy.pmakei.academy.enums.AllowedMark;

import java.io.Serializable;
import java.util.Objects;

public class Mark implements Comparable, Serializable {

  private int value;
  private Course course;
  private Teacher teacher;
  private String feedback;
  private Enum<AllowedMark> allowedMarkEnum;

  public Mark(Teacher teacher, Course course, int i, String feedback) {
    this.course = course;
    this.value = i;
    this.teacher = teacher;
    this.feedback = feedback;
    allowedMarkEnum = AllowedMark.values()[i - 1];
  }

  public Course getCourse() {
    return course;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Mark mark = (Mark) o;
    return value == mark.value
        && Objects.equals(course, mark.course)
        && Objects.equals(teacher, mark.teacher)
        && Objects.equals(feedback, mark.feedback);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, course.getCourseName(), teacher.getPersonalId(), feedback);
  }

  @Override
  public int compareTo(Object o) {
    int result = this.value - ((Mark) o).value;
    return result;
  }

  @Override
  public String toString() {
    return "Оценка = "
        + value
        + ", по курсу = "
        + course.getCourseName()
        + ", преподаватель = "
        + teacher.getName()
        + " "
        + teacher.getSurname()
        + ", отзыв преподавателя = "
        + feedback
        + ".";
  }
}
