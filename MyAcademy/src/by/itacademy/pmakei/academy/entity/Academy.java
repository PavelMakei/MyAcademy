package by.itacademy.pmakei.academy.entity;

import by.itacademy.pmakei.academy.dao.BaseDao;
import by.itacademy.pmakei.academy.enums.AllowedMark;
import by.itacademy.pmakei.academy.interfaces.Dao;
import by.itacademy.pmakei.academy.utils.AcademyUtils;

import java.io.File;
import java.util.*;


public class Academy {

    //realize singleton:
    private static Academy academy;// create PRIVATE STATIC reference

    private Academy() {
    }//PRIVATE constructor to avoid client applications to use constructor

    public static Academy getInstance() {
        if (academy == null) {//if there is no academy object
            academy = new Academy(); //create academy object
        }
        return academy;
    }

    public static String inputFolder;
    public static String outputFolder;
    public static String logsFolder;

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
        String commonFolder = "src"
                +(File.separator)
                +("by")
                +(File.separator)
                +("itacademy")
                +(File.separator)
                +("pmakei")
                +(File.separator)
                +("academy")
                +(File.separator);

// Папка с входными файлами
        inputFolder += commonFolder
                +("files")
                +(File.separator)
                +("input")
                +(File.separator);

// Папка с выходными файлами
        outputFolder += commonFolder
                +("files")
                +(File.separator)
                +("output")
                +(File.separator);

// Папка с логами
        logsFolder += commonFolder
                +("files")
                +(File.separator)
                +("logs")
                +(File.separator);


    }
//TODO 3 menus: as admin, as teacher as student


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


    private void printAllTeachers() {

        for (Teacher t : teachers) {
            System.out.println("Id - " + t.getPersonalId() + "; Name - " + t.getName() + "; Surname - " + t.getSurname() + "; age - " + t.getAge() +
                    "; course - " + t.getCourse().getCourseName() + ";");
        }
    }

    private void printAllStudents() {

        for (Student s : students) {

            System.out.println("Id - " + s.getPersonalId() + "; Name - " + s.getName() + "; Surname - " + s.getSurname() + "; age - " + s.getAge() + ";");
        }
    }


    private int getMenuPoint() {

        int selectedMenuPointFromUser;
        selectedMenuPointFromUser = AcademyUtils.getIntFromConsole();
        return (selectedMenuPointFromUser < 1 || selectedMenuPointFromUser > 9) ? 0 : selectedMenuPointFromUser;
    }



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


    public void addCourse(String courseName) {
        courses.put(courseName, new Course(courseName));
    }


    private void setMark() {//todo add corect work with enum

        Teacher teacher;
        Student student;

        printAllTeachers();
        System.out.println();
        int teacherId = getIdFromConsole("teacher");
        Human human = getHumanById(teachers, teacherId);
        if (human == null) {
            System.out.println("Teacher with this id doesn't exist");
            return;
        } else {
            teacher = (Teacher) human;
        }

        printAllStudents();
        System.out.println();
        int studentId = getIdFromConsole("student");
        human = getHumanById(students, studentId);
        if (human == null) {
            System.out.println("Student with this id doesn't exist");
            return;
        } else {
            student = (Student) human;
        }
        int markValue;
        do {
            AcademyUtils.clearScreen();
            System.out.println("Set score (1-5) to student");
            System.out.println(Arrays.toString(AllowedMark.values()));
            int gotMarkValue = AcademyUtils.getIntFromConsole();
            markValue = gotMarkValue < 1 || gotMarkValue > 5 ? 0 : gotMarkValue;

        } while (markValue == 0);

        String feedback;
        AcademyUtils.clearScreen();
        System.out.println("Write feedback");
        feedback = AcademyUtils.getStringFromConsole();
        teacher.setMark(student, (markValue - 1), feedback);//  real mark value 0-4, not 1-5

    }


    private void addStudentToCourse() {

        printAllStudents();
        int studentId = getIdFromConsole("student");

        Human h = getHumanById(students, studentId);
        if (h == null) {
            System.out.println("Student with this id doesn't exist");
            return;
        }
        Student student = (Student) h;

        Course course = null;
        course = AcademyUtils.getCourseNameFromUser();
        processAddStudentToCourse(student, course);

    }

    private void addNewStudent() {

        AcademyUtils.clearScreen();
        System.out.println("Enter new student name:");
        String name = AcademyUtils.getStringFromConsole();
        System.out.println("Enter new student surname:");
        String surname = AcademyUtils.getStringFromConsole();
        int age = 0;

        do {
            System.out.println("Enter new student age");
            int gotAgeFromUser = AcademyUtils.getIntFromConsole();
            age = (gotAgeFromUser > 15 && gotAgeFromUser < 100) ? gotAgeFromUser : 0;

        } while (age == 0);

        addStudent(name, surname, age);
        System.out.println("New student has been added");

    }


    public void processAddStudentToCourse(Student student, Course course) {

        student.addCourse(course);
        System.out.println("Student has been added to course");
    }

    public void getStudentInfoById() {

        printAllStudents();
        int id = getIdFromConsole("student");
        Human h = getHumanById(students, id);
        if (h == null) {
            System.out.println("\nStudent with id " + id + " doesn't exist");
            return;
        } else {
            Student s = (Student) h;
            System.out.println("\nStudent's name: " + s.getName() + "; Surname: " + s.getSurname() + "; age : " + s.getAge());
            System.out.println("Joins courses :");
            for (Course course : s.getCourses()) {
                System.out.println(course.getCourseName());
            }
            System.out.println("\nHas marks: \n");
            for (Mark mark : s.getMarks()) {
                System.out.println("Course : " + mark.getCourse().getCourseName() + "; Teacher : " + mark.getTeacher().getName() + " " +
                        mark.getTeacher().getSurname() + "; mark : " + AllowedMark.values()[mark.getValue()] + ";\nfeedback : " + mark.getFeedback() + ".\n");
            }
        }
    }
    public void menu() {
        while (true) {
            System.out.println("===========================================================");
            System.out.println("Выберите пользователя:");
            System.out.println("1. Администратор");
            System.out.println("2. Преподаватель");
            System.out.println("3. Студент");
            System.out.println("0. Выход");
            System.out.println("===========================================================");

            switch (AcademyUtils.getIntFromConsole()){
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
    }

    private void teacherMenu() {
        System.out.println( "teacher menu");
    }

    private void adminMenu() {
        while(true){
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

            switch (AcademyUtils.getIntFromConsole()){
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

                    //TODO добавить остальные пункты




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

}