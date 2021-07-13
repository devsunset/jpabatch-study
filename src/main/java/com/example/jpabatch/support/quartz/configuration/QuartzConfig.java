package com.example.jpabatch.support.quartz.configuration;

import static org.quartz.JobBuilder.newJob;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import com.example.jpabatch.support.quartz.job.BatchJob;
import com.example.jpabatch.support.quartz.job.TestJob;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuartzConfig {

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void start() {
        JobDetail testJobDetail = buildJobDetail(TestJob.class, "testjob", "test", "quartz test job",new HashMap<String,String>());
        JobDetail batchJobDetail = buildJobDetail(BatchJob.class, "batchjob", "batch", "batch job",new HashMap<String,String>());
        try {

            if (scheduler.checkExists(testJobDetail.getKey())){
                scheduler.deleteJob(testJobDetail.getKey());
            }

            if (scheduler.checkExists(batchJobDetail.getKey())){
                scheduler.deleteJob(batchJobDetail.getKey());
            }

			scheduler.scheduleJob(testJobDetail, buildJobTrigger("* * * * * ?"));
            scheduler.scheduleJob(batchJobDetail, buildJobTrigger("0/10 * * * * ?"));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
    }

    public Trigger buildJobTrigger(String scheduleExp) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
    }

    @SuppressWarnings("unchecked")
    public JobDetail buildJobDetail(Class job, String name, String group, String desc , HashMap<String,String> params) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);
        return newJob(job)
                .withIdentity(name, group)
                .withDescription(desc)
                .usingJobData(jobDataMap)
                .build();
    }
}
