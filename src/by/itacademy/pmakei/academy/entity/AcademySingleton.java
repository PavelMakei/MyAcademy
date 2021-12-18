package by.itacademy.pmakei.academy.entity;

import by.itacademy.pmakei.academy.exceptions.IncorrectHumanIdException;
import by.itacademy.pmakei.academy.utils.AcademyUtils;
import by.itacademy.pmakei.academy.utils.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
// TODO вынести обработку исключений по неверному ID  отдельный метод?

public class AcademySingleton {

  // realize singleton:
  private static AcademySingleton academySingleton; // create PRIVATE STATIC reference
  public static StringBuilder saveFolder;
  private List<Student> students;
  private List<Teacher> teachers;
  private List<Course> courses;
  private AcademyUtils academyUtils;

  private
  AcademySingleton() {} // PRIVATE constructor to avoid client applications to use constructor

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

    // Папка для сохранения
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
    // List list = new ArrayList(students);
    return this.students;
  }

  public List<Teacher> getTeachers() {
    // List list = new ArrayList(teachers);
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
      System.out.println("===========================================================");
      System.out.println("Список преподавателей пуст");
      System.out.println("===========================================================");
      return;
    }
    if (academySingleton.getCourses().size() == 0) {
      System.out.println("===========================================================");
      System.out.println("Список курсов пуст");
      System.out.println("===========================================================");
      return;
    }

    Teacher foundTeacher = null;
    Course foundCourse = null;
    Human human = null;
    Course course = null;
    while (true) {
      AcademyUtils.printAllTeachers(getTeachers());
      System.out.println("===========================================================");
      System.out.println("Введите Id преподавателя");
      System.out.println("===========================================================");
      try {
        human = getHumanById(getTeachers(), AcademyUtils.getIntFromConsole());
        foundTeacher = (Teacher) human;
        break;
      } catch (IncorrectHumanIdException ex) { // TODO добавить данные в логирование?
        System.out.println("===========================================================");
        System.out.println("Введены некорректные данные, повторите");
        System.out.println("===========================================================");
        Logger.writeLogToFile(ex);
      }
    }
    while (true) {
      AcademyUtils.printAllCourses();
      System.out.println("===========================================================");
      System.out.println("Введите название курса");
      System.out.println("===========================================================");
      course = getCourseByCourseName(AcademyUtils.getStringFromConsole());
      if (course == null) {
        System.out.println("===========================================================");
        System.out.println("Введены некорректные данные, повторите");
        System.out.println("===========================================================");
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
    System.out.println("===========================================================");
    System.out.println("Преподаватель назначен на курс");
    System.out.println("===========================================================");
  }

  public void addTeacher(String name, String surname, int age) {
    teachers.add(new Teacher(Human.getHumanId(), name, surname, age));
  }

  public void addStudent(String name, String surname, int age) {
    students.add(new Student(Human.getHumanId(), name, surname, age));
  }
  // TODO добавить принт списка курсов?
  public void addCourse(String courseName) {
    if (academySingleton.getCourses().size() != 0) {
      for (Course course : academySingleton.getCourses()) {
        if (course.getCourseName().equals(courseName)) {
          System.out.println("===========================================================");
          System.out.println("Данный курс уже существует");
          System.out.println("===========================================================");
          return;
        }
      }
    }
    courses.add(new Course(courseName));
    System.out.println("===========================================================");
    System.out.println("Курс успешно добавлен");
    System.out.println("===========================================================");
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
          System.out.println("===========================================================");
          System.out.println("Вы вошли как администратор");
          System.out.println("===========================================================");
          adminMenu();
          break;
        case 2:
          System.out.println("===========================================================");
          System.out.println("Вы вошли как преподаватель");
          System.out.println("===========================================================");
          teacherMenu();
          break;
        case 3:
          System.out.println("===========================================================");
          System.out.println("Вы вошли как студент");
          System.out.println("===========================================================");
          studentMenu();
          break;
        case 4:
          System.out.println("===========================================================");
          System.out.println("Выход");
          System.out.println("===========================================================");
          AcademyUtils.saveArchive();
          System.exit(0);
          break;
        default:
          System.out.println("===========================================================");
          System.out.println("Некорректный ввод, повторите");
          System.out.println("===========================================================");
          break;
      }
    }
  }

  private void studentMenu() {
    if (getTeachers().size() == 0) {
      System.out.println("===========================================================");
      System.out.println("Список студентов пуст. Добавьте студента в меню администратора");
      System.out.println("===========================================================");
      return;
    }
    System.out.println("Выберите студента");
    Student student;
    Object object;
    while (true) {
      AcademyUtils.printAllStudents(getStudents());
      System.out.println("===========================================================");
      System.out.println("Введите Id студента");
      System.out.println("===========================================================");
      try {
        object = getHumanById(getStudents(), AcademyUtils.getIntFromConsole());
        student = (Student) object;
        break;
      } catch (IncorrectHumanIdException ex) { // TODO добавить логирование?

        System.out.println("===========================================================");
        System.out.println("Введён некорректный Id, повторите");
        System.out.println("===========================================================");
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
          System.out.println("===========================================================");
          System.out.println("Записаться на курс");
          System.out.println("===========================================================");
          addStudentToCourse(student);
          break;

        case 2:
          System.out.println("===========================================================");
          System.out.println("Просмотреть оценки");
          System.out.println("===========================================================");
          AcademyUtils.printAllMarks(student.getMarks());
          break;

        case 3:
          System.out.println("===========================================================");
          System.out.println("Выход");
          System.out.println("===========================================================");
          return;

        default:
          System.out.println("===========================================================");
          System.out.println("Некорректный ввод, повторите");
          System.out.println("===========================================================");
          break;
      }
    }
  }

  private void addStudentToCourse(Student student) { // TODO optimize
    Course course;
    while (true) {

      AcademyUtils.printAllCourses();
      System.out.println("===========================================================");
      System.out.println("Введите название курса");
      System.out.println("===========================================================");
      course = getCourseByCourseName(AcademyUtils.getStringFromConsole());

      if (course == null) {
        System.out.println("===========================================================");
        System.out.println("Введены некорректные данные, повторите");
        System.out.println("===========================================================");
        continue;
      }
      boolean isStudentRegistred = false;
      // проверить наличие
      if (student.getCourses().size() != 0) { // если список курсов студента не равен 0

        for (Course tempCourse : student.getCourses()) {
          if (tempCourse.equals(course)) {
            System.out.println("===========================================================");
            System.out.println("Студент уже был записан на данный курс");
            System.out.println("===========================================================");
            isStudentRegistred = true;
            return;
          }
        }
      }

      if (!isStudentRegistred) {
        student.addCourse(course);
        System.out.println("===========================================================");
        System.out.println("Студент успешно записан на данный курс");
        System.out.println("===========================================================");
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
        System.out.println("===========================================================");
        System.out.println(
            "Список преподавателей пуст. Добавьте преподавателя в меню администратора");
        System.out.println("===========================================================");
        return;
      }
      AcademyUtils.printAllTeachers(getTeachers());
      System.out.println("===========================================================");
      System.out.println("Введите Id преподавателя");
      System.out.println("===========================================================");
      try {
        object = getHumanById(getTeachers(), AcademyUtils.getIntFromConsole());
        teacher = (Teacher) object;
        break;
      } catch (IncorrectHumanIdException ex) { // TODO добавить логирование?
        System.out.println("===========================================================");
        System.out.println("Введён некорректный Id, повторите");
        System.out.println("===========================================================");
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
          System.out.println("===========================================================");
          System.out.println("Список учеников на курсе");
          System.out.println("===========================================================");
          AcademyUtils.printAllStudentsOnCourse(teacher.getCourse());
          break;

        case 2:
          System.out.println("===========================================================");
          System.out.println("Поставить оценку студенту");
          System.out.println("===========================================================");
          AcademyUtils.setMarkToStudent(teacher);
          break;

        case 3:
          System.out.println("===========================================================");
          System.out.println("Выход");
          System.out.println("===========================================================");
          return;

        default:
          System.out.println("===========================================================");
          System.out.println("Некорректный ввод, повторите");
          System.out.println("===========================================================");
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
          System.out.println("===========================================================");
          System.out.println("Список преподавателей:");
          System.out.println("===========================================================");
          AcademyUtils.printAllTeachers(teachers);
          break;
        case 2:
          System.out.println("===========================================================");
          System.out.println("Список студентов:");
          System.out.println("===========================================================");
          AcademyUtils.printAllStudents(students);
          break;
        case 3:
          System.out.println("===========================================================");
          System.out.println("Список курсов:");
          System.out.println("===========================================================");
          AcademyUtils.printAllCourses();
          break;
        case 4:
          System.out.println("===========================================================");
          System.out.println("Добавить преподавателя");
          System.out.println("===========================================================");
          createNewTeacher();
          break;
        case 5:
          System.out.println("===========================================================");
          System.out.println("Добавить студента");
          System.out.println("===========================================================");
          createNewStudent();
          break;
        case 6:
          System.out.println("===========================================================");
          System.out.println("Добавить курс");
          System.out.println("===========================================================");
          createNewCourse();
          break;
        case 7:
          System.out.println("===========================================================");
          System.out.println("Назначить преподавателя на курс");
          System.out.println("===========================================================");
          mapTeacherToCourse();
          break;

          // TODO добавить остальные пункты

        case 8:
          System.out.println("===========================================================");
          System.out.println("Выход");
          System.out.println("===========================================================");
          return;

        default:
          System.out.println("===========================================================");
          System.out.println("Некорректный ввод, повторите");
          System.out.println("===========================================================");
          break;
      }
    }
  }

  private void createNewCourse() {
    addCourse(getStringFromUser("название", "курса"));
  }

  private void createNewStudent() {
    addStudent(
        getStringFromUser("имя", "студента"),
        getStringFromUser("фамилию", "студента"),
        getAgeFromUser());

    System.out.println("===========================================================");
    System.out.println("Студент успешно добавлен");
    System.out.println("===========================================================");
  }

  private void createNewTeacher() {
    addTeacher(
        getStringFromUser("имя", "преподавателя"),
        getStringFromUser("фамилию", "преподавателя"),
        getAgeFromUser());

    System.out.println("===========================================================");
    System.out.println("Преподаватель успешно добавлен");
    System.out.println("===========================================================");
  }

  private int getAgeFromUser() {
    int age;
    while (true) {
      System.out.println("===========================================================");
      System.out.println("Введите возраст");
      System.out.println("===========================================================");
      age = AcademyUtils.getIntFromConsole();

      if (age < 18 || age > 100) {
        System.out.println("===========================================================");
        System.out.println("Введите возрвст (целое число от 18 до 100)");
        System.out.println("===========================================================");
      } else {
        return age;
      }
    }
  }

  private String getStringFromUser(String string1, String string2) {
    System.out.println("===========================================================");
    System.out.println("Введите " + string1 + " " + string2);
    System.out.println("===========================================================");
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
