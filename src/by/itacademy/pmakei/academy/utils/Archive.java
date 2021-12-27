package by.itacademy.pmakei.academy.utils;

import java.io.Serializable;
import java.util.List;

/**
 * Class to save and load common data
 *
 * @author Pavel Makei
 * @version 1.0
 */
public class Archive implements Serializable {
    private List students;
  private List teachers;
  private List courses;
    private int humanIdCount;

  /** Open constructor without param */
  public Archive() {}
  ;

  /**
   * Open constructor
   *
   * @param  students - list of students
   * @param  teachers - list of teachers
   * @param  courses - list of courses
   * @param  humanIdCount - int humanIdCount
   */
  public Archive(List students, List teachers, List courses, int humanIdCount) {
    this.students = students;
    this.teachers = teachers;
    this.courses = courses;
    this.humanIdCount = humanIdCount;
  }

  /**
   * Function returns field {@link Archive#students}
   *
   * @return list of students
   */
  public List getStudents() {
        return students;
    }

  /**
   * Function set field {@link Archive#students}
   *
   * @param students - list of students
   */
  public void setStudents(List students) {
        this.students = students;
    }

  /**
   * Function returns field {@link Archive#teachers}
   *
   * @return list of teachers
   */
  public List getTeachers() {
    return teachers;
    }

  /**
   * Function set field {@link Archive#teachers}
   *
   * @param teachers - list of teachers
   */
  public void setTeachers(List teachers) {
    this.teachers = teachers;
    }

  /**
   * Function returns field {@link Archive#courses}
   *
   * @return list of courses
   */
  public List getCourses() {
    return courses;
    }

  /**
   * Function set field {@link Archive#courses}
   *
   * @param courses  - list of courses
   */
  public void setCourses(List courses) {
    this.courses = courses;
    }

  /**
   * Function returns field {@link Archive#humanIdCount}
   *
   * @return int humanIdCount
   */
  public int getHumanIdCount() {
        return humanIdCount;
    }

  /**
   * Function set field {@link Archive#humanIdCount}
   *
   * @param humanIdCount - int humanIdCount
   */
  public void setHumanIdCount(int humanIdCount) {
        this.humanIdCount = humanIdCount;
    }

}
