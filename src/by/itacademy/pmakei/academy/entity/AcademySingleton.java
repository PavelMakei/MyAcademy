package by.itacademy.pmakei.academy.entity;

import by.itacademy.pmakei.academy.exceptions.IncorrectHumanIdException;
import by.itacademy.pmakei.academy.utils.AcademyUtils;
import by.itacademy.pmakei.academy.utils.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class AcademySingleton {

  private static AcademySingleton academySingleton;
  public static StringBuilder saveFolder;
  private List<Student> students;
  private List<Teacher> teachers;
  private List<Course> courses;

  private AcademySingleton() {}

  public static AcademySingleton getInstance() {
    if (academySingleton == null) {
      academySingleton = new AcademySingleton();
    }
    return academySingleton;
  }

  public void initialize() {

    courses = new LinkedList<>();
    teachers = new ArrayList<>();
    students = new ArrayList<>();

    // Папка для сохранения/восстановления архива (БД)
    saveFolder = new StringBuilder();
    saveFolder
        .append("src")
        .append(File.separator)
        .append("by")
        .append(File.separator)
        .append("itacademy")
        .append(File.separator)
        .append("pmakei")
        .append(File.separator)
        .append("academy")
        .append(File.separator)
        .append("file")
        .append(File.separator)
        .append("save")
        .append(File.separator);
  }

  public List<Student> getStudents() {
    return this.students;
  }

  public List<Teacher> getTeachers() {
    return this.teachers;
  }

  public List<Course> getCourses() {
    return courses;
  }

  public Human getHumanById(List list, int id) throws IncorrectHumanIdException {

    Human human;
    Iterator<Human> it = list.iterator();
    while (it.hasNext()) {
      human = it.next();
      if (id == human.getPersonalId()) {
        return human;
      }
    }
    throw new IncorrectHumanIdException("Введён некорректный Id", id);
  }

  public Course getCourseByCourseName(String courseName) {

    Course course = null;
    for (Course courseObj : courses) {
      if (courseName.equals(courseObj.getCourseName())) {
        course = courseObj;
        break;
      }
    }
    return course;
  }

  public void mapTeacherToCourse() {
    if (academySingleton.getTeachers().size() == 0) {
      AcademyUtils.printPartOfMenu("Список преподавателей пуст");
      return;
    }
    if (academySingleton.getCourses().size() == 0) {
      AcademyUtils.printPartOfMenu("Список курсов пуст");
      return;
    }

    Teacher foundTeacher = null;
    Course foundCourse = null;
    Human human = null;
    Course course = null;
    while (true) {
      AcademyUtils.printAllTeachers(getTeachers());
      AcademyUtils.printPartOfMenu("Введите Id преподавателя");
      try {
        human = getHumanById(getTeachers(), AcademyUtils.getIntFromConsole());
        foundTeacher = (Teacher) human;
        break;
      } catch (IncorrectHumanIdException ex) {
        AcademyUtils.printPartOfMenu("Введены некорректные данные, повторите");
        Logger.writeLogToFile(ex);
      }
    }
    while (true) {
      AcademyUtils.printAllCourses();
      AcademyUtils.printPartOfMenu("Введите название курса");
      course = getCourseByCourseName(AcademyUtils.getStringFromConsole());
      if (course == null) {
        AcademyUtils.printPartOfMenu("Введены некорректные данные, повторите");
      } else {
        foundCourse = (Course) course;
        break;
      }
    }
    // снять предыдущего преподавателя с курса, если он там был.
    if (foundCourse.getTeacher() != null) {
      foundCourse.getTeacher().setCourse(null);
    }
    foundTeacher.setCourse(foundCourse);
    foundCourse.setTeacher(foundTeacher);
    AcademyUtils.printPartOfMenu("Преподаватель назначен на курс");
  }

  public void addTeacher(String name, String surname, int age) {
    teachers.add(new Teacher(Human.getHumanId(), name, surname, age));
  }

  public void addStudent(String name, String surname, int age) {
    students.add(new Student(Human.getHumanId(), name, surname, age));
  }

  public void addCourse(String courseName) {
    if (academySingleton.getCourses().size() != 0) {
      for (Course course : academySingleton.getCourses()) {
        if (course.getCourseName().equals(courseName)) {
          AcademyUtils.printPartOfMenu("Данный курс уже существует");
          return;
        }
      }
    }
    courses.add(new Course(courseName));
    AcademyUtils.printPartOfMenu("Курс успешно добавлен");
  }

  public void mainMenu() {
    while (true) {
      System.out.println("===========================================================");
      System.out.println("Выберите пользователя:");
      System.out.println("1. Администратор");
      System.out.println("2. Преподаватель");
      System.out.println("3. Студент");
      System.out.println("4. Выход");
      System.out.println("===========================================================");

      switch (AcademyUtils.getIntFromConsole()) {
        case 1:
          AcademyUtils.printPartOfMenu("Вы вошли как администратор");
          adminMenu();
          break;
        case 2:
          AcademyUtils.printPartOfMenu("Вы вошли как преподаватель");
          teacherMenu();
          break;
        case 3:
          AcademyUtils.printPartOfMenu("Вы вошли как студент");
          studentMenu();
          break;
        case 4:
          AcademyUtils.printPartOfMenu("Выход");
          AcademyUtils.saveArchive();
          System.exit(0);
          break;
        default:
          AcademyUtils.printPartOfMenu("Некорректный ввод, повторите");
          break;
      }
    }
  }

  private void studentMenu() {
    if (getTeachers().size() == 0) {
      AcademyUtils.printPartOfMenu(
          "Список студентов пуст. Добавьте студента в меню администратора");
      return;
    }
    System.out.println("Выберите студента");
    Student student;
    Object object;
    while (true) {
      AcademyUtils.printAllStudents(getStudents());
      AcademyUtils.printPartOfMenu("Введите Id студента");
      try {
        object = getHumanById(getStudents(), AcademyUtils.getIntFromConsole());
        student = (Student) object;
        break;
      } catch (IncorrectHumanIdException ex) {
        AcademyUtils.printPartOfMenu("Введён некорректный Id, повторите");
        Logger.writeLogToFile(ex);
      }
    }
    while (true) {
      System.out.println("===========================================================");
      System.out.println("Выберите действие:");
      System.out.println("1. Записаться на курс");
      System.out.println("2. Просмотреть оценки");
      System.out.println("3. Выход");
      System.out.println("===========================================================");

      switch (AcademyUtils.getIntFromConsole()) {
        case 1:
          AcademyUtils.printPartOfMenu("Записаться на курс");
          addStudentToCourse(student);
          break;
        case 2:
          AcademyUtils.printPartOfMenu("Просмотреть оценки");
          AcademyUtils.printAllMarks(student.getMarks());
          break;
        case 3:
          AcademyUtils.printPartOfMenu("Выход");
          return;
        default:
          AcademyUtils.printPartOfMenu("Некорректный ввод, повторите");
          break;
      }
    }
  }

  private void addStudentToCourse(Student student) {
    Course course;
    while (true) {
      AcademyUtils.printAllCourses();
      AcademyUtils.printPartOfMenu("Введите название курса");
      course = getCourseByCourseName(AcademyUtils.getStringFromConsole());
      if (course == null) {
        AcademyUtils.printPartOfMenu("Введены некорректные данные, повторите");
        continue;
      }

      boolean isStudentRegistred = false;
      if (student.getCourses().size() != 0) { // если список курсов студента не равен 0

        for (Course tempCourse : student.getCourses()) {
          if (tempCourse.equals(course)) {
            AcademyUtils.printPartOfMenu("Студент уже был записан на данный курс");
            isStudentRegistred = true;
            return;
          }
        }
      }

      if (!isStudentRegistred) {
        student.addCourse(course);
        AcademyUtils.printPartOfMenu("Студент успешно записан на данный курс");
        return;
      }
    }
  }

  private void teacherMenu() {
    System.out.println("Выберите преподавателя");
    Teacher teacher;
    Object object;

    while (true) {
      if (getTeachers().size() == 0) {
        AcademyUtils.printPartOfMenu(
            "Список преподавателей пуст. Добавьте преподавателя в меню администратора");
        return;
      }
      AcademyUtils.printAllTeachers(getTeachers());
      AcademyUtils.printPartOfMenu("Введите Id преподавателя");
      try {
        object = getHumanById(getTeachers(), AcademyUtils.getIntFromConsole());
        teacher = (Teacher) object;
        break;
      } catch (IncorrectHumanIdException ex) {
        AcademyUtils.printPartOfMenu("Введён некорректный Id, повторите");
        Logger.writeLogToFile(ex);
      }
    }

    while (true) {
      System.out.println("===========================================================");
      System.out.println("Выберите действие:");
      System.out.println("1. Просмотреть список учеников на курсе");
      System.out.println("2. Поставить оценку студенту");
      System.out.println("3. Выход");
      System.out.println("===========================================================");
      switch (AcademyUtils.getIntFromConsole()) {
        case 1:
          AcademyUtils.printPartOfMenu("Список учеников на курсе");
          AcademyUtils.printAllStudentsOnCourse(teacher.getCourse());
          break;
        case 2:
          AcademyUtils.printPartOfMenu("Поставить оценку студенту");
          AcademyUtils.setMarkToStudent(teacher);
          break;
        case 3:
          AcademyUtils.printPartOfMenu("Выход");
          return;
        default:
          AcademyUtils.printPartOfMenu("Некорректный ввод, повторите");
          break;
      }
    }
  }

  private void adminMenu() {
    while (true) {
      System.out.println("===========================================================");
      System.out.println("Выберите действие:");
      System.out.println("1. Просмотреть список преподавателей");
      System.out.println("2. Просмотреть список студентов");
      System.out.println("3. Просмотреть список курсов");
      System.out.println("4. Добавить преподавателя");
      System.out.println("5. Добавить студента");
      System.out.println("6. Добавить курс");
      System.out.println("7. Назначить преподавателя на курс");
      System.out.println("8. Выход");
      System.out.println("===========================================================");

      switch (AcademyUtils.getIntFromConsole()) {
        case 1:
          AcademyUtils.printPartOfMenu("Список преподавателей:");
          AcademyUtils.printAllTeachers(teachers);
          break;
        case 2:
          AcademyUtils.printPartOfMenu("Список студентов:");
          AcademyUtils.printAllStudents(students);
          break;
        case 3:
          AcademyUtils.printPartOfMenu("Список курсов:");
          AcademyUtils.printAllCourses();
          break;
        case 4:
          AcademyUtils.printPartOfMenu("Добавить преподавателя");
          createNewTeacher();
          break;
        case 5:
          AcademyUtils.printPartOfMenu("Добавить студента");
          createNewStudent();
          break;
        case 6:
          AcademyUtils.printPartOfMenu("Добавить курс");
          createNewCourse();
          break;
        case 7:
          AcademyUtils.printPartOfMenu("Назначить преподавателя на курс");
          mapTeacherToCourse();
          break;
        case 8:
          AcademyUtils.printPartOfMenu("Выход");
          return;

        default:
          AcademyUtils.printPartOfMenu("Некорректный ввод, повторите");
          break;
      }
    }
  }

  private void createNewCourse() {
    addCourse(getStringFromUser("название", "курса"));
  }

  private void createNewStudent() {
    addStudent(
        validateNameSurname("имя", "студента"),
        validateNameSurname("фамилию", "студента"),
        getAgeFromUser());
    AcademyUtils.printPartOfMenu("Студент успешно добавлен");
  }

  private void createNewTeacher() {
    addTeacher(
        validateNameSurname("имя", "преподавателя"),
        validateNameSurname("фамилию", "преподавателя"),
        getAgeFromUser());
    AcademyUtils.printPartOfMenu("Преподаватель успешно добавлен");
  }

  private int getAgeFromUser() {
    int age;
    while (true) {
      AcademyUtils.printPartOfMenu("Введите возраст");
      age = AcademyUtils.getIntFromConsole();

      if (age < 18 || age > 100) {
        AcademyUtils.printPartOfMenu("Введите возрвст (целое число от 18 до 100)");
      } else {
        return age;
      }
    }
  }

  private String validateNameSurname(String nameOrSurname, String role) {
    String stringFromUser;
    while (true) {
      stringFromUser = getStringFromUser(nameOrSurname, role);
      if (Pattern.matches("^(?=.{3})[A-ZА-Я][a-zа-яё]+-?[a-zа-яё]*$", stringFromUser)) {
        return stringFromUser;
      } else {
        System.out.println(
            "Введите слово с Заглавной буквы, допускаются буквы латинского и русского алфавита,\nа также один дефис");
      }
    }
  }

  private String getStringFromUser(String string1, String string2) {
    AcademyUtils.printPartOfMenu("Введите " + string1 + " " + string2);
    return AcademyUtils.getStringFromConsole();
  }

  public void setStudents(List students) {
    this.students = students;
  }

  public void setTeachers(List teachers) {
    this.teachers = teachers;
  }

  public void setCourses(List courses) {
    this.courses = courses;
  }
}
