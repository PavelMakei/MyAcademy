package by.itacademy.pmakei.academy.utils;

import by.itacademy.pmakei.academy.entity.*;
import by.itacademy.pmakei.academy.exceptions.IncorrectHumanIdException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public final class AcademyUtils {

  private static AcademySingleton academySingleton;

  static {
    academySingleton = AcademySingleton.getInstance();
  }

  private AcademyUtils() {}

  public static List sortHumans(List humans, Comparator comparator) {

    Collections.sort(humans, comparator);
    return humans;
  }

  public static void printAllTeachers(List<Teacher> teachers) {
    Collections.sort(teachers, new ComparatorHumanByName());
    //        Collections.sort(teachers,new CompareHumanBySurname());
    sortMenu(teachers, "преподавателей");

    teachers.forEach(teacher -> {
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
    });
  }

  private static void sortMenu(List humans, String role) {
    printPartOfMenu("Отсортировать список  " + role);

    while (true) {
      System.out.println("===========================================================");
      System.out.println("Отсортировать по:");
      System.out.println("1. Id");
      System.out.println("2. Имени");
      System.out.println("3. Фамилии");
      System.out.println("===========================================================");

      switch (getIntFromConsole()) {
        case 1:
          printPartOfMenu("Отсортированы по Id");
          AcademyUtils.sortHumans(humans, new ComparatorHumanById());
          return;
        case 2:
          printPartOfMenu("Отсортированы по имени");
          AcademyUtils.sortHumans(humans, new ComparatorHumanByName());
          return;
        case 3:
          printPartOfMenu("Отсортированы по фамилии");
          AcademyUtils.sortHumans(humans, new ComparatorHumanBySurname());
          return;
        default:
          printPartOfMenu("Некорректный ввод, повторите");
          AcademyUtils.sortHumans(humans, new ComparatorHumanBySurname());
          break;
      }
    }
  }

  public static void printPartOfMenu(String string) {
    System.out.println("===========================================================");
    System.out.println("\"" + string + "\"");
    System.out.println("===========================================================");
  }

  public static void printAllStudents(List<Student> students) {
    if (students == null) {
      System.out.println("Нет зарегистрированных студентов");
      return;
    }
    sortMenu(students, "студентов");

    students.forEach(
        (student) -> {
          System.out.println(
              "Id- "
                  + student.getPersonalId()
                  + "; "
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
        });
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

  public static void printAllCourses() {

    academySingleton.getCourses().forEach((name) -> System.out.println("Название курса: " + name));
  }

  public static void printAllMarks(List marks) {

    if (marks.size() == 0) {
      System.out.println("пока нет оценок.");
      return;
    }
    marks.forEach(System.out::println);
  }

  public static void printAllStudentsOnCourse(Course course) {
    if (course == null) {
      System.out.println("У данного преподавателя нет курсов");
      return;
    }
    List<Student> studentsOnCourse = getListStudentsOnCourse(course);
    if (studentsOnCourse.size() == 0) {
      System.out.println("Студентов  на курсе не зарегистрировано");
      return;
    }
    studentsOnCourse.forEach(student -> {
      System.out.println(
              "Id "
                      + student.getPersonalId()
                      + ", имя и фамилия: "
                      + student.getName()
                      + " "
                      + student.getSurname()
                      + ";");
    });
  }

  private static List getListStudentsOnCourse(Course course) {
    return academySingleton.getStudents().stream()
        .filter(
            student ->
                student.getCourses().stream().anyMatch(courseVra -> courseVra.equals(course)))
        .collect(Collectors.toList());
  }

  public static void setMarkToStudent(Teacher teacher) {

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
      printPartOfMenu("Нет студентов, на курсе этого преподавателя");
      return;
    }
    while (true) {
      AcademyUtils.printAllStudents(academySingleton.getStudents());
      printPartOfMenu("Введите Id студента");
      try {
        human = academySingleton.getHumanById(studentOnCourse, AcademyUtils.getIntFromConsole());
      } catch (IncorrectHumanIdException ex) {
        printPartOfMenu("Введён некорректный Id, выход");
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
      printPartOfMenu("Ввеите оценку студенту от 1 до 5");
      markValue = AcademyUtils.getIntFromConsole();
      if (markValue < 1 || markValue > 5) {
        printPartOfMenu("Введено некорректное значение, повторите");
      } else {
        break;
      }
    }
    printPartOfMenu("Введите отзыв о студенте");
    feedback = getStringFromConsole();
    student.setMark(new Mark(teacher, teachersCourse, markValue, feedback));
    printPartOfMenu("Оценка успешно добавлена");
  }

  public static void saveArchiveDataTofile(Archive archive, String file) throws IOException {

    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(new FileOutputStream(file));
    oos.writeObject(archive);
    } finally {
      if (oos != null) {
        oos.close();
      }
    }
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
      printPartOfMenu("Архив успешно загружен");
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
      Logger.writeLogToFile(
          "Файл не найден "
              + new File(String.valueOf(academySingleton.saveFolder) + "save.ser")
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
      Logger.writeLogToFile(
          "Класс не найден "
              + new File(String.valueOf(academySingleton.saveFolder) + "save.ser")
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
      Logger.writeLogToFile(
          "Ошибка чтения файла "
              + new File(String.valueOf(academySingleton.saveFolder) + "save.ser")
                  .getAbsolutePath());
      return;
    }

  }

  private static Archive loadArchiveFromFile(String file)
      throws IOException, ClassNotFoundException, FileNotFoundException {

    Archive archive = new Archive();
    ObjectInputStream ois = null;

    try {
      ois = new ObjectInputStream(new FileInputStream(file));
    archive = (Archive) ois.readObject();
      return archive;
    } finally {
      if (ois != null) {
    ois.close();
      }
    }
  }
}
