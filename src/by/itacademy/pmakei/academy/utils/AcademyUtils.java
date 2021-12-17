package by.itacademy.pmakei.academy.utils;

import by.itacademy.pmakei.academy.entity.*;
import by.itacademy.pmakei.academy.exceptions.IncorrectHumanIdException;

import java.io.*;
import java.util.*;

public final class AcademyUtils {

  private static AcademySingleton academySingleton;

  static {
    academySingleton = AcademySingleton.getInstance();
  }

  private AcademyUtils() {}

  public static List sortHumanByName(List humans) {

    Collections.sort(humans);
    return humans;
  }

  public static void printAllTeachers(List teachers) {
    Collections.sort(teachers, new ComparatorHumanByName());
    //        Collections.sort(teachers,new CompareHumanBySurname());

    for (Object t : teachers) {
      Teacher teacher = (Teacher) t;
      System.out.println(
          "Id - "
              + teacher.getPersonalId()
              + "; "
              + "имя и фамилия - "
              + teacher.getName()
              + " "
              + teacher.getSurname()
              + "; "
              + "возраст - "
              + teacher.getAge()
              + "; ");
      System.out.println(
          "курс - "
              + (teacher.getCourse() == null
                  ? "не назначен на курс \n"
                  : teacher.getCourse().getCourseName() + ".\n"));
    }
  }

  public static void printAllStudents(List students) {
    Comparator<Student> comparatorByName = Comparator.comparing(obj -> obj.getName());
    Collections.sort(students, comparatorByName);
    //        Comparator<Student> comparatorBySurname = Comparator.comparing(obj->obj.getSurname());
    //        Collections.sort(students, comparatorBySurname);

    for (Object s : students) {
      Student student = (Student) s;
      System.out.println(
          "Id - "
              + student.getPersonalId()
              + "; "
              + "имя и фамилия - "
              + student.getName()
              + " "
              + student.getSurname()
              + "; "
              + "возраст - "
              + student.getAge()
              + "; ");
      if (student.getCourses().size() == 0) {
        System.out.println("Не записался на курсы");
      } else {
        System.out.println("Записался на курсы:");
        for (Course course : student.getCourses()) {
          System.out.println(course.getCourseName() + ";");
        }
      }
      System.out.println();
    }
  }

  public static void clearScreen() {

    // this method doesn't really clear screen. but make  some new text  more noticeable
    for (int i = 0; i < 20; i++) {
      System.out.println();
    }
  }

  public static int getIntFromConsole() {

    Scanner scanner = new Scanner(System.in);
    return (scanner.hasNextInt()) ? scanner.nextInt() : 0;
  }

  public static String getStringFromConsole() {
    Scanner scanner = new Scanner(System.in);
    String string = scanner.nextLine();
    return string;
  }

  public static Course getCourseNameFromUser() {

    Course course = null;

    do {
      clearScreen();

      System.out.println("Введите название кукрса: \n");
      System.out.println("Доступные курсы");
      AcademyUtils.printAllCourses();
      String courseNameFromUser = getStringFromConsole();

      course = academySingleton.getCourseByCourseName(courseNameFromUser);
    } while (course == null);

    return course;
  }

  public static void printAllCourses() {

    academySingleton
        .getCourses()
        .forEach((name, course) -> System.out.println("Название курса: " + name));
  }

  public static void printAllMarks(List marks) {
    Mark mark;
    if (marks.size() == 0) {
      System.out.println("пока нет оценок.");
      return;
    }
    for (Object object : marks) {
      mark = (Mark) object;
      System.out.println(mark);
    }
  }

  public static void printAllStudentsOnCourse(Course course) {
    Student student;
    if (course == null) {
      System.out.println("Преподаватель не ведёт курсов");
      return;
    }
    List studentsOnCourse = getListStudentsOnCourse(course);
    if (studentsOnCourse.size() == 0) {
      System.out.println("Студентов  на курсе не зарегистрировано");
      return;
    }
    for (Object studentObject : studentsOnCourse) {
      student = (Student) studentObject;
      System.out.println(
          "Id "
              + student.getPersonalId()
              + ", имя и фамилия: "
              + student.getName()
              + " "
              + student.getSurname()
              + ";");
    }
  }

  private static List getListStudentsOnCourse(Course course) {
    List studentsOnCourse = new ArrayList();
    List allStudents = academySingleton.getStudents();
    for (Student student : academySingleton.getStudents()) {
      if (student.getCourses().size() == 0) {
        continue;
      }
      for (Course courseFromList : student.getCourses()) {
        if (courseFromList.equals(course)) {
          studentsOnCourse.add(student);
        }
      }
    }
    return studentsOnCourse;
  }

  public static void setMarkToStudent(Teacher teacher) {

    /*TODO
    1. if teacher has course
    2. get studentsOnCourse
    3. select student
    4. check if student has mark on this course/ or delete from course after mark settings?
    5. get from user mark value
    6. get feedback from user
    7. add mark
    8. del student from course?
     */

    List studentOnCourse = getListStudentsOnCourse(teacher.getCourse());
    Student student;
    Object human;
    Course teachersCourse;
    boolean hasMarkOnThisCourse = false;
    while (true) {

      System.out.println("===========================================================");
      System.out.println("Введите Id студента");
      System.out.println("===========================================================");
      try {
        human = academySingleton.getHumanById(studentOnCourse, AcademyUtils.getIntFromConsole());
      } catch (IncorrectHumanIdException ex) { // TODO дописать логирование?
        System.out.println("===========================================================");
        System.out.println("Введён некорректный Id, выход");
        System.out.println("===========================================================");
        return;
      }

      student = (Student) human;
      break;
    }
    for (Mark marks : student.getMarks()) {}
  }

  public static void saveListDataTofile(List list, String file) {
    // TODO correct exceptions

    try {
      FileOutputStream fos = new FileOutputStream(file);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(list);
      oos.close();
      fos.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  public static List readListDataFromFile(String file) {
      List list = new ArrayList();
    // TODO correct Exceptions
    try {
      FileInputStream fis = new FileInputStream(file);
      ObjectInputStream ois = new ObjectInputStream(fis);
      list = (List) ois.readObject();
      ois.close();
      return list;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return new ArrayList();// возврат пустого некоррумпированного листа
    } catch (IOException e) {
      e.printStackTrace();
      return new ArrayList();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      return new ArrayList();
    }
  }
  public static void saveMapDataTofile(Map map, String file) {
    // TODO correct exceptions

    try {
      FileOutputStream fos = new FileOutputStream(file);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(map);
      oos.close();
      fos.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
  public static Map readMapDataFromFile(String file) {
    Map map = new HashMap();
    // TODO correct Exceptions
    try {
      FileInputStream fis = new FileInputStream(file);
      ObjectInputStream ois = new ObjectInputStream(fis);
      map = (Map) ois.readObject();
      ois.close();
      return map;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return new HashMap();// возврат пустой некоррумпированной мапы
    } catch (IOException e) {
      e.printStackTrace();
      return new HashMap();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      return new HashMap();
    }
  }
}
