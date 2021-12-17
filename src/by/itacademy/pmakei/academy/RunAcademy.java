package by.itacademy.pmakei.academy;

import by.itacademy.pmakei.academy.entity.AcademySingleton;
import by.itacademy.pmakei.academy.entity.Student;
import by.itacademy.pmakei.academy.exceptions.IncorrectHumanIdException;
import by.itacademy.pmakei.academy.utils.AcademyUtils;

import java.util.ArrayList;
import java.util.List;

/** @author Pavel Makei */
public final class RunAcademy {

  AcademySingleton academySingleton;

  public static void main(String[] args) {
    RunAcademy runAcademy = new RunAcademy();
    runAcademy.run();
  }

  private void run() {

    // TODO Create, Initialize, Work

    academySingleton = AcademySingleton.getInstance();
    academySingleton.initialize();
    loadCollectionsFromFiles();
    academySingleton.mainMenu();
  }

  private void saveCollectionsToFiles() {
    AcademyUtils.saveListDataTofile(academySingleton.getStudents(), String.valueOf(academySingleton.saveFolder + "students.ser"));
    AcademyUtils.saveListDataTofile(academySingleton.getStudents(), String.valueOf(academySingleton.saveFolder + "teachers.ser"));
    AcademyUtils.saveMapDataTofile(academySingleton.getCourses(), String.valueOf(academySingleton.saveFolder + "courses.ser"));
  }

  private void loadCollectionsFromFiles() {
    academySingleton.setStudents(AcademyUtils.readListDataFromFile(String.valueOf(academySingleton.saveFolder + "students.ser")));
    academySingleton.setTeachers(AcademyUtils.readListDataFromFile(String.valueOf(academySingleton.saveFolder + "teachers.ser")));
    academySingleton.setCourses(AcademyUtils.readMapDataFromFile(String.valueOf(academySingleton.saveFolder + "courses.ser")));
  }

}
