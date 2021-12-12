package by.itacademy.pmakei.academy.utils;

import by.itacademy.pmakei.academy.entity.Academy;
import by.itacademy.pmakei.academy.entity.Course;
import by.itacademy.pmakei.academy.entity.Human;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AcademyUtils {

    private static Academy academy;

    static {
        academy = Academy.getInstance();
    }
    private AcademyUtils() {}



    // TODO: 11.12.2021  all academyUtils


    public static void sortHumanByName(List humans) {

        Collections.sort(humans);
        for (
                Object human: humans) {
            System.out.println(((Human)human).getName());
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
