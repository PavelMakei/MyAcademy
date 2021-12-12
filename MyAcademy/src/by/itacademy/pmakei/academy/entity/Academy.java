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

    public static StringBuffer inputFolder = new StringBuffer();
    public static StringBuffer outputFolder = new StringBuffer();

    private Dao studentDAO;
    private Dao teacherDAO;
    private Dao baseDAO;


    private ArrayList<Student> students;
    private ArrayList<Teacher> teachers;
    private HashMap<String, Course> courses;
    private AcademyUtils academyUtils;


    public void create() {

        courses = new HashMap<String, Course>();
        teachers = new ArrayList<>();
        students = new ArrayList<>();


        studentDAO = new BaseDao(students);
        teacherDAO = new BaseDao(teachers);


// Папка, где хранятся входные файлы
        inputFolder.append("course")
                .append(File.separator)
                .append("by")
                .append(File.separator)
                .append("itacademy")
                .append(File.separator)
                .append("first")
                .append(File.separator)
                .append("project")
                .append(File.separator)
                .append("files")
                .append(File.separator)
                .append("input")
                .append(File.separator);
// Папка, где хранятся выходные файлы
        outputFolder.append("src")
        ppend(File.separator)
        ppend("by")
        ppend(File.separator)
        ppend("pvt")
        ppend(File.separator)
        ppend("khudnitsky")
        ppend(File.separator)
        ppend("travel")
        ppend(File.separator)
        ppend("files")
        ppend(File.separator)
        ppend("output")
        ppend(File.separator);


    }
//TODO 3 menus: as admin, as teacher as student

    public void run() {

        AcademyUtils.clearScreen();
        System.out.println("Welcome to our academy! \n");

        int menu;

        do {
            System.out.println("\n");
            printMenu();
            menu = getMenuPoint();
            switch (menu) {
                case 1:
                    AcademyUtils.printAllCourses();
                    break;
                case 2:
                    printAllTeachers();
                    break;
                case 3:
                    printAllStudents();
                    break;
                case 4:
                    getStudentInfoById();
                    break;
                case 5:
                    addStudentToCourse();
                    break;
                case 6:
                    addNewStudent();
                    break;
                case 7:
                    setMark();
                    break;
                case 8:
                    return;
                default:
                    AcademyUtils.clearScreen();
                    System.out.println();

            }
        } while (menu != 0);
    }

    public List<Student> getCopyStudents() {
        List list = new ArrayList(students);
        return (List<Student>) list;
    }

    public List<Teacher> getTeachers() {
        List list = new ArrayList(teachers);
        return (List) list;
    }

    public HashMap<String, Course> getCourses() {
        return courses;
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


    private void printMenu() {

        System.out.println("1. Get all courses");
        System.out.println("2. Get all teachers");
        System.out.println("3. Get all students");
        System.out.println("4. Get student info by id");
        System.out.println("5. Add student to course");
        System.out.println("6. Add new student");
        System.out.println("7. Set mark to student");
        System.out.println("8. Exit");
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


//
}