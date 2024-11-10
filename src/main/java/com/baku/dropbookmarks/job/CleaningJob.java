package com.baku.dropbookmarks.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Map;

@Slf4j
public class CleaningJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap(); // jobExecutionContext.getMergedJobDataMap();
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            log.info("KEY: " + key + ", VALUE: " + value);
        }
       log.info("#### %%%% THIS CLEANING JOB IS RUNNING IN EVERY TWO MINUTES %%%% ####");
    }
}
