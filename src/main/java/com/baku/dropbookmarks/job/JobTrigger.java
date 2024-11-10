package com.baku.dropbookmarks.job;

import org.quartz.CronTrigger;
import org.quartz.TriggerBuilder;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class JobTrigger {

    public static CronTrigger cleanJobTrigger()
    {
        return TriggerBuilder.newTrigger()
                .withIdentity("CleanJobTrigger", "Group1")
                .withSchedule(cronSchedule("0 0/2 * * * ?"))
                .forJob("cleanJob", "Batch1")
                .build();
    }
}
