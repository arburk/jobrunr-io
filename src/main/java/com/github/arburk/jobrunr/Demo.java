package com.github.arburk.jobrunr;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import org.jobrunr.configuration.JobRunr;
import org.jobrunr.scheduling.BackgroundJob;
import org.jobrunr.storage.InMemoryStorageProvider;

public class Demo {

  public static final String CRON = "*/5 * * * * *"; //every 5 seconds
  private static final String ID = "myJobId";
  public static final String FILE_NAME = "C:\\dev\\gitRepos\\_github\\jobrunr-io\\joblog.txt";
  private static InMemoryStorageProvider storageProvider;

  public static void main(String[] args) {

    storageProvider = new InMemoryStorageProvider();
    JobRunr.configure()
        .useStorageProvider(storageProvider)
        .useBackgroundJobServer()
        .useDashboard()
        .initialize();

    FileLogger.fileName = FILE_NAME;
    System.out.println("programm started at " + new Date());
    new Demo().run();
  }

  private void run() {
    BackgroundJob.scheduleRecurrently(ID, CRON, () -> FileLogger.run());
    System.out.println("myJob was scheduled with " + CRON);
    storageProvider.getRecurringJobs().forEach(System.out::println);
    System.out.println("use http://localhost:8000/dashboard/ to view jobs");
  }


  public static class FileLogger {

    private static String fileName;

    public FileLogger(String fileName) {
      FileLogger.fileName = fileName;
      run("Created at " + new Date());
    }

    public static void run() {
      System.out.println("ping...");
      run("I was executed at " + new Date());
    }

    private static void run(String text) {
      try (FileWriter fileWriter = new FileWriter(fileName, true);
           PrintWriter printWriter = new PrintWriter(fileWriter)) {
        printWriter.println(text);
        printWriter.flush();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
