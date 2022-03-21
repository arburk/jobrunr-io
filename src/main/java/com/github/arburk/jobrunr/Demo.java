package com.github.arburk.jobrunr;

import org.jobrunr.configuration.JobRunr;
import org.jobrunr.scheduling.BackgroundJob;
import org.jobrunr.scheduling.cron.Cron;
import org.jobrunr.storage.InMemoryStorageProvider;

import java.util.Date;

public class Demo {

  public static void main(String[] args) {
    InMemoryStorageProvider storageProvider = new InMemoryStorageProvider();
    JobRunr.configure()
        .useStorageProvider(storageProvider)
        .useBackgroundJobServer()
        .initialize();

    System.out.println("programm started");

    BackgroundJob.enqueue(
        () -> System.out.println("Hello")
    );

    BackgroundJob.scheduleRecurrently(
        Cron.minutely(),
        () -> System.out.println("executed " + new Date())
    );

    System.out.println("programm stopped");
  }
}
