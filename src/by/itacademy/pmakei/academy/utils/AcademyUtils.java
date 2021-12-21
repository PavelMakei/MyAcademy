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

  public static List sortHuman(List humans, Comparator comparator) {

    Collections.sort(humans,comparator);
    return humans;
  }

  public static void printAllTeachers(List teachers) {
    Collections.sort(teachers, new ComparatorHumanByName());
    //        Collections.sort(teachers,new CompareHumanBySurname());
    sortMenu(teachers, "преподавателей");

    for (Object t : teachers) {
      Teacher teacher = (Teacher) t;
      System.out.println(
          "Id- "
              + teacher.getPersonalId()
              + "; "
//              + "имя и фамилия - "
              + teacher.getName()
              + " "
              + teacher.getSurname()
              + "; "
              + "возраст- "
              + teacher.getAge()
              + "; ");
      System.out.println(
          "курс- "
              + (teacher.getCourse() == null
                  ? "не назначен на курс \n"
                  : teacher.getCourse().getCourseName() + ".\n"));
    }
  }

  private static void sortMenu(List humans, String role) {
    System.out.println("===========================================================");
    System.out.println("Отсортировать список  " + role);
    System.out.println("===========================================================");

    while (true) {
      System.out.println("===========================================================");
      System.out.println("Отсортировать по:");
      System.out.println("1. Id");
      System.out.println("2. Имени");
      System.out.println("3. Фамилии");
      System.out.println("===========================================================");

      switch (getIntFromConsole()) {
        case 1:
          System.out.println("===========================================================");
          System.out.println("Отсортированы по Id");
          System.out.println("===========================================================");
          AcademyUtils.sortHuman(humans, new ComparatorHumanById());
          return;
        case 2:
          System.out.println("===========================================================");
          System.out.println("Отсортированы по имени");
          System.out.println("===========================================================");
          AcademyUtils.sortHuman(humans, new ComparatorHumanByName());
          return;
        case 3:
          System.out.println("===========================================================");
          System.out.println("Отсортированы по фамилии");
          System.out.println("===========================================================");
          AcademyUtils.sortHuman(humans, new ComparatorHumanBySurname());
          return;

        default:
          System.out.println("===========================================================");
          System.out.println("Введены некорректные ");
          System.out.println("===========================================================");
          AcademyUtils.sortHuman(humans, new ComparatorHumanBySurname());
          break;

      }
}
  }

  public static void printAllStudents(List students) {
    if (students == null) {
      System.out.println("Нет зарегистрированных студентов");
      return;
    }
    sortMenu(students, "студентов");
    //Comparator<Student> comparatorByName = Comparator.comparing(obj -> obj.getName());
    //Comparator<Student> comparatorBySurname = Comparator.comparing(obj->obj.getSurname());
    //Collections.sort(students, comparatorBySurname);

    for (Object s : students) {
      Student student = (Student) s;
      System.out.println(
          "Id- "
              + student.getPersonalId()
              + "; "
//              + "имя и фамилия - "
              + student.getName()
              + " "
              + student.getSurname()
              + "; "
              + "возраст- "
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

      System.out.println("Введите название курса: \n");
      System.out.println("Доступные курсы");
      AcademyUtils.printAllCourses();
      String courseNameFromUser = getStringFromConsole();

      course = academySingleton.getCourseByCourseName(courseNameFromUser);
    } while (course == null);

    return course;
  }

  public static void printAllCourses() {

    academySingleton.getCourses().forEach((name) -> System.out.println("Название курса: " + name));
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
    /*
    1. delete from course after mark settings?
    2. del student from course after marking?
     */

    Student student;
    Object human;
    Course teachersCourse;
    List studentOnCourse;
    List marks;
    Mark mark;
    int markValue;
    String feedback;

    teachersCourse = teacher.getCourse();
    if (teachersCourse == null) {
      System.out.println("===========================================================");
      System.out.println(
          "Преподаватель " + teacher.getName() + " " + teacher.getSurname() + " не ведёт курсы");
      System.out.println("===========================================================");
      return;
    }
    studentOnCourse = getListStudentsOnCourse(teacher.getCourse());
    if (studentOnCourse.size() == 0) {
      System.out.println("===========================================================");
      System.out.println("Нет студентов, на курсе этого преподавателя");
      System.out.println("===========================================================");
      return;
    }
    while (true) {
      AcademyUtils.printAllStudents(academySingleton.getStudents());

      System.out.println("===========================================================");
      System.out.println("Введите Id студента");
      System.out.println("===========================================================");
      try {
        human = academySingleton.getHumanById(studentOnCourse, AcademyUtils.getIntFromConsole());
      } catch (IncorrectHumanIdException ex) {
        System.out.println("===========================================================");
        System.out.println("Введён некорректный Id, выход");
        System.out.println("===========================================================");
        return;
      }

      student = (Student) human;
      break;
    }
    marks = student.getMarks();
    if (marks.size() != 0) {
      for (Object objMark : marks) {
        mark = (Mark) objMark;
        if (mark.getCourse().equals(teachersCourse)) {
          System.out.println("===========================================================");
          System.out.println(
              "Оценка студенту "
                  + student.getName()
                  + " "
                  + student.getSurname()
                  + " уже была выставлена");
          System.out.println("===========================================================");
          return;
        }
      }
    }
    while (true) {
      System.out.println("===========================================================");
      System.out.println("Ввеите оценку студенту от 1 до 5");
      System.out.println("===========================================================");
      markValue = AcademyUtils.getIntFromConsole();
      if (markValue < 1 || markValue > 5) {
        System.out.println("===========================================================");
        System.out.println("Введено некорректное значение, повторите");
        System.out.println("===========================================================");
      } else {
        break;
      }
    }
    System.out.println("===========================================================");
    System.out.println("Введите отзыв о студенте");
    System.out.println("===========================================================");
    feedback = getStringFromConsole();
    student.setMark(new Mark(teacher, teachersCourse, markValue, feedback));
    System.out.println("===========================================================");
    System.out.println("Оценка успешно добавлена");
    System.out.println("===========================================================");
  }

  public static void saveArchiveDataTofile(Archive archive, String file) throws IOException {

    FileOutputStream fos = new FileOutputStream(file);
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(archive);
    oos.close();
    fos.close();
  }

  public static void saveArchive() {
    Archive archive = new Archive();

    archive.setCourses(academySingleton.getCourses());
    archive.setStudents(academySingleton.getStudents());
    archive.setTeachers(academySingleton.getTeachers());
    archive.setHumanIdCount(Human.getHumanId());
    try {
      saveArchiveDataTofile(archive, String.valueOf(academySingleton.saveFolder) + "save.ser");
      System.out.println("===========================================================");
      System.out.println("Архив успешно записан");
      System.out.println(
          "Расположение архива:"
              + new File(String.valueOf(academySingleton.saveFolder) + "save.ser")
                  .getAbsolutePath());
      System.out.println("===========================================================");
    } catch (IOException e) {
      System.out.println("===========================================================");
      System.out.println("Внимание! Произошла ошибка записи архива!");
      System.out.println(
          "Проверьте доступ к файлу"
              + new File(String.valueOf(academySingleton.saveFolder) + "save.ser")
                  .getAbsolutePath());
      System.out.println("===========================================================");
      e.printStackTrace();
    }
  }

  public static void loadArchive() {
    Archive archive;

    try {
      archive = loadArchiveFromFile(String.valueOf(academySingleton.saveFolder) + "save.ser");

      academySingleton.setCourses(archive.getCourses());
      academySingleton.setTeachers(archive.getTeachers());
      academySingleton.setStudents(archive.getStudents());
      Human.setHumanIdCount(archive.getHumanIdCount());
      System.out.println("===========================================================");
      System.out.println("Архив успешно загружен");
      System.out.println("===========================================================");
      return;
    } catch (FileNotFoundException e) {
      System.out.println("===========================================================");
      System.out.println(
          "Внимание! Файлы архва не найдены, программа запущена с пустой базой данных");
      System.out.println(
          "Проверьте наличие файла "
              + new File(String.valueOf(academySingleton.saveFolder) + "save.ser")
                  .getAbsolutePath());
      System.out.println("===========================================================");
      academySingleton.setCourses(new LinkedList());
      academySingleton.setTeachers(new ArrayList());
      academySingleton.setStudents(new ArrayList());
      Human.setHumanIdCount(1);
      Logger.writeLogToFile("Файл не найден "+ new File(String.valueOf(academySingleton.saveFolder) + "save.ser")
              .getAbsolutePath());
      return;

    } catch (ClassNotFoundException e) {
      System.out.println("===========================================================");
      System.out.println(
          "Внимание! Файлы архва повреждены, программа запущена с пустой базой данных");
      System.out.println(
          "Проверьте файл "
              + new File(String.valueOf(academySingleton.saveFolder) + "save.ser")
                  .getAbsolutePath());
      System.out.println("===========================================================");
      academySingleton.setCourses(new LinkedList());
      academySingleton.setTeachers(new ArrayList());
      academySingleton.setStudents(new ArrayList());
      Human.setHumanIdCount(1);
      Logger.writeLogToFile("Класс не найден "+ new File(String.valueOf(academySingleton.saveFolder) + "save.ser")
              .getAbsolutePath());
      return;
    } catch (IOException e) {
      System.out.println("===========================================================");
      System.out.println(
          "Внимание! не удалось прочитать фал архива, программа запущена с пустой базой данных");
      System.out.println(
          "Проверьте файл "
              + new File(String.valueOf(academySingleton.saveFolder) + "save.ser")
                  .getAbsolutePath());
      System.out.println("===========================================================");
      academySingleton.setCourses(new LinkedList());
      academySingleton.setTeachers(new ArrayList());
      academySingleton.setStudents(new ArrayList());
      Human.setHumanIdCount(1);
      Logger.writeLogToFile("Ошибка чтения файла "+ new File(String.valueOf(academySingleton.saveFolder) + "save.ser")
              .getAbsolutePath());
      return;
    }
  }

  private static Archive loadArchiveFromFile(String file)
      throws IOException, ClassNotFoundException, FileNotFoundException {

    Archive archive = new Archive();

    FileInputStream fis = new FileInputStream(file);
    ObjectInputStream ois = new ObjectInputStream(fis);
    archive = (Archive) ois.readObject();
    ois.close();
    return archive;
  }
}
