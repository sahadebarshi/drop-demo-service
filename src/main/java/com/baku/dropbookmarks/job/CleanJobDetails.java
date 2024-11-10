package com.baku.dropbookmarks.job;

import org.quartz.JobDetail;

import static org.quartz.JobBuilder.newJob;

public class CleanJobDetails {

    public static JobDetail addJobDetails()
    {
        return newJob(CleaningJob.class)
                .withIdentity("cleanJob", "Batch1")
                .usingJobData("jobSays", "Hello World!")
                .build();
    }
}
