package com.baku.dropbookmarks.job;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Properties;

@Slf4j
public class QuartzSchedulerManager {


    private Scheduler scheduler;
    private SchedulerFactory schedulerFactory;

    public QuartzSchedulerManager(){
        try {
            Properties props = new Properties();
            props.setProperty("org.quartz.threadPool.threadCount", "1");
            //this.scheduler = StdSchedulerFactory.getDefaultScheduler();
            this.schedulerFactory = new StdSchedulerFactory(props);
            this.scheduler = schedulerFactory.getScheduler();

        } catch (Exception e) {
            log.error("Error initializing Quartz Scheduler", e);
        }
    }

    public void startQuartzScheduler() {
        try {
            if (scheduler != null && !scheduler.isStarted()) {
                scheduler.start();
                log.info("QUARTZ SCHEDULER STARTED....");
                JobDetail jd = CleanJobDetails.addJobDetails();
                CronTrigger ct = JobTrigger.cleanJobTrigger();
                scheduler.scheduleJob(jd, ct);
            }
        } catch (Exception e) {
            log.error("Error starting Quartz Scheduler", e);
        }
    }

    @PreDestroy
    public void stopQuartzScheduler() {
        try {
            if (scheduler != null && scheduler.isStarted()) {
                // Gracefully shut down the scheduler
                scheduler.shutdown(true);  // true to wait for running jobs to finish
                log.info("QUARTZ SCHEDULER STOPPED....");
            }
        } catch (Exception e) {
            log.error("Error stopping Quartz Scheduler", e);
        }
    }
}
