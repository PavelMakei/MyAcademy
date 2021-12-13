package by.itacademy.pmakei.academy.utils;

import by.itacademy.pmakei.academy.entity.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public final class AcademyUtils {

    private static Academy academy;

    static {
        academy = Academy.getInstance();
    }

    private AcademyUtils() {
    }

    public static List sortHumanByName(List humans) {

        Collections.sort(humans);
        return humans;
    }

    public static void printAllTeachers(List teachers) {
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
                            ? "не назначен на курс"
                            : teacher.getCourse().getCourseName() + ".\n"));
        }
    }

    public static void printAllStudents(List students) {
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
                System.out.println("Записался на курсы:\n");
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

            course = academy.getCourseByCourseName(courseNameFromUser);
        } while (course == null);

        return course;
    }

    public static void printAllCourses() {

        academy.getCourses().forEach((name, course) -> System.out.println("Название курса: " + name));
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
        if(studentsOnCourse.size() == 0){
            System.out.println("Студентов  на курсе не зарегистрировано");
            return;
        }
        for (Object studentObject : studentsOnCourse){
            student = (Student)studentObject;
            System.out.println("Id " + student.getPersonalId() +
                    ", имя и фамилия: " + student.getName() +
                    " " + student.getSurname() + ";");


        }


    }

    private static List getListStudentsOnCourse(Course course) {
        List studentsOnCourse = new ArrayList();
        List allStudents = academy.getCopyStudents();
        for (Student student : academy.getCopyStudents()) {
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
        Object object;
        Course teachersCourse;
        boolean hasMarkOnThisCourse = false;
        while (true) {

            System.out.println("===========================================================");
            System.out.println("Введите Id студента");
            System.out.println("===========================================================");
            object = academy.getHumanById(studentOnCourse, AcademyUtils.getIntFromConsole());
            if (object == null) {
                System.out.println("===========================================================");
                System.out.println("Введён некорректный Id, выход");
                System.out.println("===========================================================");
                return;
            } else {
                student = (Student) object;
                break;
            }
        }
            for (Mark marks : student.getMarks()){



        }






        }
    }

