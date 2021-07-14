package com.example.jpabatch;

import javax.batch.operations.JobRestartException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableBatchProcessing
public class JpabatchApplication {

    public static JobParameters getJobParameters() {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("reqSeq", System.currentTimeMillis()+"-Main");
        jobParametersBuilder.addString("createDate", "2021-07-14");
        jobParametersBuilder.addString("startDate", "2021-07-14");
        jobParametersBuilder.addString("endDate", "2021-07-14");
        return jobParametersBuilder.toJobParameters();
    }

    public static void main(String[] args)
            throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, org.springframework.batch.core.repository.JobRestartException {
        ConfigurableApplicationContext ctx = SpringApplication.run(JpabatchApplication.class, args);
        JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");
        Job job1= (Job) ctx.getBean("simpleJob");
        Job job2= (Job) ctx.getBean("stepNextJob");
        Job job3= (Job) ctx.getBean("stepNextConditionalJob");
        Job job4= (Job) ctx.getBean("deciderJob");
        Job job5= (Job) ctx.getBean("jdbcCursorItemReaderJob");
        Job job6= (Job) ctx.getBean("jdbcPagingItemReaderJob");
        Job job7= (Job) ctx.getBean("jpaPagingItemReaderJob");
        Job job8= (Job) ctx.getBean("jdbcBatchItemWriterJob");
        Job job9= (Job) ctx.getBean("jpaItemWriterJob");
        Job job10= (Job) ctx.getBean("customItemWriterJob");
        Job job11= (Job) ctx.getBean("processorConvertJob");
        Job job12= (Job) ctx.getBean("multiThreadPagingBatch");
        Job job13= (Job) ctx.getBean("multiThreadCursorBatch");
        Job job14= (Job) ctx.getBean("partitionLocalBatch");

        jobLauncher.run(job1,getJobParameters());
        jobLauncher.run(job2,getJobParameters());
        jobLauncher.run(job3,getJobParameters());
        jobLauncher.run(job4,getJobParameters());
        jobLauncher.run(job5,getJobParameters());
        jobLauncher.run(job6,getJobParameters());
        jobLauncher.run(job7,getJobParameters());
        jobLauncher.run(job8,getJobParameters());
        jobLauncher.run(job9,getJobParameters());
        jobLauncher.run(job10,getJobParameters());
        jobLauncher.run(job11,getJobParameters());
        jobLauncher.run(job12,getJobParameters());
        jobLauncher.run(job13,getJobParameters());
        jobLauncher.run(job14,getJobParameters());
    }

}
 