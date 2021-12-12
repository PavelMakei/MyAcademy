package by.itacademy.pmakei.academy.utils;

import by.itacademy.pmakei.academy.entity.*;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public final class AcademyUtils {

    private static Academy academy;

    static {
        academy = Academy.getInstance();
    }
    private AcademyUtils() {}



    // TODO: 11.12.2021  all academyUtils


    public static List sortHumanByName(List humans) {

        Collections.sort(humans);
        return humans;
    }

    public static void printAllTeachers(List teachers){
        for(Object t : teachers){
            Teacher teacher = (Teacher) t;
            System.out.println("Id - " + teacher.getPersonalId() + "; "
                    + "name & surname - " + teacher.getName() + " "
                    + teacher.getSurname() + "; "
                    + "age - " + teacher.getAge() + "; "
                    +  "course - " + teacher.getCourse().getCourseName() + ".\n");

        }
    }

    public static void printAllStudents(List students){
        for(Object s : students){
            Student student= (Student) s;
            System.out.println("Id - " + student.getPersonalId() + "; "
                    + "name & surname - " + student.getName() + " "
                    + student.getSurname() + "; "
                    + "age - " + student.getAge() + "; ");
            if (student.getCourses().size() == 0){
                System.out.println("Doesn't join any course");
            }else {
                System.out.println("Join coursrs:\n");
                for (Course course : student.getCourses()) {
                    System.out.println(course.getCourseName() + ";");
                }
            }
            System.out.println();
        }
    }



    public static void clearScreen() {

        //this method doesn't really clear screen. but make  some new text  more noticeable
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

            System.out.println("Enter course name: \n");
            System.out.println("Available courses");
            AcademyUtils.printAllCourses();
            String courseNameFromUser = getStringFromConsole();

            course = academy.getCourseByCourseName(courseNameFromUser);
        } while (course == null);

        return course;
    }

    public static void printAllCourses() {

        academy.getCourses().forEach((s, c) -> System.out.println("Course name: " + s));

    }

}
