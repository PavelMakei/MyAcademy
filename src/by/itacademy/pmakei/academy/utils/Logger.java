package by.itacademy.pmakei.academy.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/** @author Pavel Makei */
public final class Logger {

  private static StringBuilder file = new StringBuilder();

  static {
    file.append("src")
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
        .append("logs")
        .append(File.separator)
        .append("log.txt");
  }

  public static void writeLogToFile(String message) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    try (PrintWriter printWriter =
        new PrintWriter(new BufferedWriter(new FileWriter(String.valueOf(file), true))); ) {
      printWriter.println(simpleDateFormat.format(Calendar.getInstance().getTime()));
      printWriter.println(message);
    } catch (IOException e) {
      Logger.writeLogToFile(e);
    }
  }

  public static void writeLogToFile(Throwable errors) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    try (PrintWriter printWriter =
        new PrintWriter(new BufferedWriter(new FileWriter(String.valueOf(file), true))); ) {
      printWriter.println(simpleDateFormat.format(Calendar.getInstance().getTime()));
      printWriter.println(errors);
    } catch (IOException e) {
      Logger.writeLogToFile(e);
    }
  }
}
