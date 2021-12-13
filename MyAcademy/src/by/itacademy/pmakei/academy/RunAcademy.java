package by.itacademy.pmakei.academy;

import by.itacademy.pmakei.academy.entity.Academy;
import by.itacademy.pmakei.academy.entity.Student;

/**
 * @author Pavel Makei
 */
public final class RunAcademy {

    Academy academy;

  public static void main(String[] args) {
    RunAcademy runAcademy = new RunAcademy();
    runAcademy.run();
  }



    private void run() {

      //TODO Create, Initialize, Work


      academy = Academy.getInstance();
      academy.initialize();
      fillDataOfAcademy();
      academy.mainMenu();


    }

    private void fillDataOfAcademy() {
        academy.addCourse("Philosophy");
        academy.addCourse("Programming");
        academy.addCourse("Biology");
        academy.addCourse("Language");
        academy.addCourse("Sociology");
        academy.addCourse("Physic");

        academy.addTeacher("Zachar", "Fteacher", 52);
        academy.addTeacher("Benya", "Steacher", 84);
        academy.addTeacher("Collito", "Italian", 32);
        academy.addTeacher("Denim", "Tornado", 50);
        academy.addTeacher("Egor", "Mudko", 52);
        academy.addTeacher("Fernando", "Alonso", 44);

        academy.addStudent("Sava", "First", 18);
        academy.addStudent("Boris", "Second", 19);
        academy.addStudent("Cop", "Ment", 20);
        academy.addStudent("Derby", "Ryan", 21);
        academy.addStudent("Emelya", "Pechnik", 22);
        academy.addStudent("Frosya", "Polotskaya", 23);

        academy.mapTeacherToCourse(6, "Physic");
        academy.mapTeacherToCourse(5, "Philosophy");
        academy.mapTeacherToCourse(4, "Programming");
        academy.mapTeacherToCourse(3, "Biology");
        academy.mapTeacherToCourse(2, "Language");
        academy.mapTeacherToCourse(1, "Sociology");


        //__________________________________________________

        academy.processAddStudentToCourse((Student) academy.getHumanById(academy.getCopyStudents(), 7), academy.getCourseByCourseName("Philosophy"));
        academy.processAddStudentToCourse((Student) academy.getHumanById(academy.getCopyStudents(), 7), academy.getCourseByCourseName("Programming"));
        academy.processAddStudentToCourse((Student) academy.getHumanById(academy.getCopyStudents(), 7), academy.getCourseByCourseName("Biology"));
        academy.processAddStudentToCourse((Student) academy.getHumanById(academy.getCopyStudents(), 7), academy.getCourseByCourseName("Language"));
        //academy.processAddStudentToCourse((Student) academy.getHumanById(academy.getCopyStudents(), 7), academy.getCourseByCourseName("Sociology"));
        academy.processAddStudentToCourse((Student) academy.getHumanById(academy.getCopyStudents(), 7), academy.getCourseByCourseName("Physic"));

    }

}
