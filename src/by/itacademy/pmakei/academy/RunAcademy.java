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

    academySingleton = AcademySingleton.getInstance();
    academySingleton.initialize();
    AcademyUtils.loadArchive();
    academySingleton.mainMenu();
  }



}
