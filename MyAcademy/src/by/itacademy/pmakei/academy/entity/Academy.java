package by.itacademy.pmakei.academy.entity;

import by.itacademy.pmakei.academy.dao.BaseDao;
import by.itacademy.pmakei.academy.exceptions.IncorrectEnterException;
import by.itacademy.pmakei.academy.interfaces.Dao;
import by.itacademy.pmakei.academy.utils.AcademyUtils;

import java.io.File;
import java.util.*;

public class Academy {

  // realize singleton:
  private static Academy academy; // create PRIVATE STATIC reference

  private Academy() {} // PRIVATE constructor to avoid client applications to use constructor

  public static Academy getInstance() {
    if (academy == null) { // if there is no academy object
      academy = new Academy(); // create academy object
    }
    return academy;
  }

  public static StringBuilder
      inputFolder; // String с конкатом загромождает память по причине стрингПула, StringBuffer для
  // потокобезопасных, для остальных предпочтительнее StringBuilder
  public static StringBuilder outputFolder;
  public static StringBuilder logsFolder;

  private Dao studentDAO;
  private Dao teacherDAO;
  private Dao baseDAO;

  private List<Student> students;
  private List<Teacher> teachers;
  private HashMap<String, Course> courses;
  private AcademyUtils academyUtils;

  public void initialize() {

    courses = new HashMap<String, Course>();
    teachers = new ArrayList<>();
    students = new ArrayList<>();

    studentDAO = new BaseDao(students);
    teacherDAO = new BaseDao(teachers);

    // Общая папка
    StringBuilder commonFolder = new StringBuilder();

    commonFolder
        .append("src")
        .append(File.separator)
        .append("by")
        .append(File.separator)
        .append("itacademy")
        .append(File.separator)
        .append("pmakei")
        .append(File.separator)
        .append("academy")
        .append(File.separator);

    // Папка с входными файлами
    inputFolder = new StringBuilder();
    inputFolder
        .append(commonFolder)
        .append("files")
        .append(File.separator)
        .append("input")
        .append(File.separator);

    // Папка с выходными файлами
    outputFolder = new StringBuilder();
    outputFolder
        .append(commonFolder)
        .append("files")
        .append(File.separator)
        .append("output")
        .append(File.separator);

    // Папка с логами
    logsFolder = new StringBuilder();
    logsFolder
        .append(commonFolder)
        .append("files")
        .append(File.separator)
        .append("logs")
        .append(File.separator);
  }
  // TODO 3 menus: as admin, as teacher as student

  public List<Student> getCopyStudents() {
    List list = new ArrayList(students);
    return (List<Student>) list;
  }

  public List<Teacher> getCopyTeachers() {
    List list = new ArrayList(teachers);
    return (List) list;
  }

  public HashMap<String, Course> getCourses() {
    return courses;
  }

  public Human getHumanById(List list, int id) {

    Human human;
    Iterator<Human> it = list.iterator();
    while (it.hasNext()) {
      human = it.next();
      if (id == human.getPersonalId()) {
        return human;
      }
    }
    return null;
  }

  private int getIdFromConsole(String typeOfHuman) {

    int gotId;
    do {
      //            clearScreen();
      System.out.println("Enter id of " + typeOfHuman);
      int selectedIntFromConsole = AcademyUtils.getIntFromConsole();
      gotId = selectedIntFromConsole <= 0 ? 0 : selectedIntFromConsole;

    } while (gotId == 0);

    return gotId;
  }

  //  private void printAllStudents() {
  //
  //    for (Student s : students) {
  //
  //      System.out.println(
  //          "Id - "
  //              + s.getPersonalId()
  //              + "; Name - "
  //              + s.getName()
  //              + "; Surname - "
  //              + s.getSurname()
  //              + "; age - "
  //              + s.getAge()
  //              + ";");
  //    }
  //  }

  public Course getCourseByCourseName(String courseName) {

    Course course = null;
    for (Map.Entry<String, Course> entry : courses.entrySet()) {
      if (courseName.equals(entry.getKey())) {
        course = entry.getValue();
        break;
      }
    }
    return course;
  }

  public void mapTeacherToCourse() {

    Teacher foundTeacher = null;
    Course foundCourse = null;
    Object object;
    while (true) {
      AcademyUtils.printAllTeachers(getCopyTeachers());
      System.out.println("===========================================================");
      System.out.println("Введите Id преподавателя");
      System.out.println("===========================================================");
      object = getHumanById(getCopyTeachers(), AcademyUtils.getIntFromConsole());
      if (object == null) {
        System.out.println("===========================================================");
        System.out.println("Введены некорректные данные, повторите");
        System.out.println("===========================================================");
      } else {
        foundTeacher = (Teacher) object;
        break;
      }
    }
    while (true) {
      AcademyUtils.printAllCourses();
      System.out.println("===========================================================");
      System.out.println("Введите название курса");
      System.out.println("===========================================================");
      object = getCourseByCourseName(AcademyUtils.getStringFromConsole());
      if (object == null) {
        System.out.println("===========================================================");
        System.out.println("Введены некорректные данные, повторите");
        System.out.println("===========================================================");
      } else {
        foundCourse = (Course) object;
        break;
      }
    }
    // снять предыдущего преподавателя с курса, если он там был.
    // TODO переделать в МАП?
    if (foundCourse.getTeacher() != null) {
      foundCourse.getTeacher().setCourse(null);
    }
    foundTeacher.setCourse(foundCourse);
    foundCourse.setTeacher(foundTeacher);
    System.out.println("===========================================================");
    System.out.println("Преподаватель назначен на курс");
    System.out.println("===========================================================");
  }
  // метод для мапинга по шаблону
  // TODO в окончательном варианте - удалить
  public void mapTeacherToCourse(int idTeacher, String courseName) {

    Teacher foundTeacher = null;
    Course foundCourse = null;

    for (Teacher t : teachers) {
      if (t.getPersonalId() == (idTeacher)) {
        foundTeacher = t;
        break;
      }
    }

    foundCourse = courses.get(courseName);

    if (foundTeacher == null) {
      System.out.println("Teacher not found");
      return;
    } else if (foundCourse == null) {
      System.out.println("Course not found");
      return;
    } else {
      foundTeacher.setCourse(foundCourse);
      foundCourse.setTeacher(foundTeacher);
    }
  }

  public void addTeacher(String name, String surname, int age) {
    teacherDAO.add(new Teacher(Human.getHumanId(), name, surname, age));
  }

  public void addStudent(String name, String surname, int age) {
    studentDAO.add(new Student(Human.getHumanId(), name, surname, age));
  }
  // TODO Оставть добавление курса?
  public void addCourse(String courseName) {
    courses.put(courseName, new Course(courseName));
  }

  private void setMark() { // todo add corect work with enum

    //        Teacher teacher;
    //        Student student;
    //
    //        printAllTeachers();
    //        System.out.println();
    //        int teacherId = getIdFromConsole("teacher");
    //        Human human = getHumanById(teachers, teacherId);
    //        if (human == null) {
    //            System.out.println("Teacher with this id doesn't exist");
    //            return;
    //        } else {
    //            teacher = (Teacher) human;
    //        }
    //
    //        printAllStudents();
    //        System.out.println();
    //        int studentId = getIdFromConsole("student");
    //        human = getHumanById(students, studentId);
    //        if (human == null) {
    //            System.out.println("Student with this id doesn't exist");
    //            return;
    //        } else {
    //            student = (Student) human;
    //        }
    //        int markValue;
    //        do {
    //            AcademyUtils.clearScreen();
    //            System.out.println("Set score (1-5) to student");
    //            System.out.println(Arrays.toString(AllowedMark.values()));
    //            int gotMarkValue = AcademyUtils.getIntFromConsole();
    //            markValue = gotMarkValue < 1 || gotMarkValue > 5 ? 0 : gotMarkValue;
    //
    //        } while (markValue == 0);
    //
    //        String feedback;
    //        AcademyUtils.clearScreen();
    //        System.out.println("Write feedback");
    //        feedback = AcademyUtils.getStringFromConsole();
    //        teacher.setMark(student, (markValue - 1), feedback);//  real mark value 0-4, not 1-5

  }

  // TODO оставить метод?
  public void processAddStudentToCourse(Student student, Course course) {

    student.addCourse(course);
    System.out.println("Student has been added to course");
  }

  public void mainMenu() {
    while (true) {
      System.out.println("===========================================================");
      System.out.println("Выберите пользователя:");
      System.out.println("1. Администратор");
      System.out.println("2. Преподаватель");
      System.out.println("3. Студент");
      System.out.println("0. Выход");
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
        case 0:
          System.out.println("===========================================================");
          System.out.println("Выход");
          System.out.println("===========================================================");
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
    System.out.println("student menu");
    Student student;
    Object object;
    while (true) {
      AcademyUtils.printAllStudents(getCopyStudents());
      System.out.println("===========================================================");
      System.out.println("Введите Id студента");
      System.out.println("===========================================================");
      object = getHumanById(getCopyStudents(), AcademyUtils.getIntFromConsole());
      if (object == null) {
        System.out.println("===========================================================");
        System.out.println("Введён некорректный Id, повторите");
        System.out.println("===========================================================");
      } else {
        student = (Student) object;
        break;
      }
    }
    while (true) {
      System.out.println("===========================================================");
      System.out.println("Выберите действие:");
      System.out.println("1. Записаться на курс");
      System.out.println("2. Просмотреть оценки");
      System.out.println("0. Выход");
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
          // addStudentToCourse(student);
          break;

        case 0:
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
    Object object;
    Course course;
    while (true) {

      AcademyUtils.printAllCourses();
      System.out.println("===========================================================");
      System.out.println("Введите название курса");
      System.out.println("===========================================================");
      object = getCourseByCourseName(AcademyUtils.getStringFromConsole());

      if (object == null) {
        System.out.println("===========================================================");
        System.out.println("Введены некорректные данные, повторите");
        System.out.println("===========================================================");
        continue;
      }
      boolean isStudentRegistred = false;
      course = (Course) object;
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
    System.out.println("teacher menu");
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
      System.out.println("6. Назначить преподавателя на курс");
      System.out.println("0. Выход");
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
          System.out.println("Назначить преподавателя на курс");
          System.out.println("===========================================================");
          mapTeacherToCourse();
          break;

          // TODO добавить остальные пункты

        case 0:
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

  private void createNewStudent() {
    addStudent(
        getNameOrSurnameFromUser("имя", "студента"),
        getNameOrSurnameFromUser("фамилию", "студента"),
        getAgeFromUser());

    System.out.println("===========================================================");
    System.out.println("Студент успешно добавлен");
    System.out.println("===========================================================");
  }

  private void createNewTeacher() {
    addTeacher(
        getNameOrSurnameFromUser("имя", "преподавателя"),
        getNameOrSurnameFromUser("фамилию", "преподавателя"),
        getAgeFromUser());

    System.out.println("===========================================================");
    System.out.println("Преподаватель успешно добавлен");
    System.out.println("===========================================================");
  }

  private int getAgeFromUser() {
    int age;
    while (true) {
      System.out.println("===========================================================");
      System.out.println("Введите возрвст");
      System.out.println("===========================================================");
      age = AcademyUtils.getIntFromConsole();
      try {
        if (age < 18 || age > 100) {
          throw new IncorrectEnterException("entered int age <18 or >100", age);
        }
        return age;
      } catch (IncorrectEnterException e) {
        System.out.println("===========================================================");
        System.out.println("Введите возрвст (целое число от 18 до 100)");
        System.out.println("===========================================================");

        // TODO ptint to log e.printStackTrace();
      }
    }
  }

  private String getNameOrSurnameFromUser(String nameOrSurname, String humanType) {
    System.out.println("===========================================================");
    System.out.println("Введите " + nameOrSurname + " " + humanType);
    System.out.println("===========================================================");
    return AcademyUtils.getStringFromConsole();
  }
}
