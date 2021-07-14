package com.example.jpabatch.support.schedule;

import com.example.jpabatch.sample.job.CustomItemWriterJobConfiguration;
import com.example.jpabatch.sample.job.DeciderJobConfiguration;
import com.example.jpabatch.sample.job.FileItemReadWriterJdbcJobConfiguration;
import com.example.jpabatch.sample.job.JdbcBatchItemWriterJobConfiguration;
import com.example.jpabatch.sample.job.JdbcCursorItemReaderJobConfiguration;
import com.example.jpabatch.sample.job.JdbcPagingItemReaderJobConfiguration;
import com.example.jpabatch.sample.job.JpaItemWriterJobConfiguration;
import com.example.jpabatch.sample.job.JpaPagingItemReaderJobConfiguration;
import com.example.jpabatch.sample.job.MultiThreadCursorConfiguration;
import com.example.jpabatch.sample.job.MultiThreadPagingConfiguration;
import com.example.jpabatch.sample.job.PartitionLocalConfiguration;
import com.example.jpabatch.sample.job.ProcessorConvertJobConfiguration;
import com.example.jpabatch.sample.job.SimpleJobConfiguration;
import com.example.jpabatch.sample.job.StepNextConditionalJobConfiguration;
import com.example.jpabatch.sample.job.StepNextJobConfiguration;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableScheduling
public class BatchScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private SimpleJobConfiguration simpleJobConfiguration;
    @Autowired
    private StepNextJobConfiguration stepNextJobConfiguration;
    @Autowired
    private StepNextConditionalJobConfiguration stepNextConditionalJobConfiguration;
    @Autowired
    private DeciderJobConfiguration deciderJobConfiguration;
    @Autowired
    private JdbcCursorItemReaderJobConfiguration jdbcCursorItemReaderJobConfiguration;
    @Autowired
    private JdbcPagingItemReaderJobConfiguration jdbcPagingItemReaderJobConfiguration;
    @Autowired
    private JpaPagingItemReaderJobConfiguration jpaPagingItemReaderJobConfiguration;
    @Autowired
    private JdbcBatchItemWriterJobConfiguration jdbcBatchItemWriterJobConfiguration;
    @Autowired
    private JpaItemWriterJobConfiguration jpaItemWriterJobConfiguration;
    @Autowired
    private CustomItemWriterJobConfiguration customItemWriterJobConfiguration;
    @Autowired
    private ProcessorConvertJobConfiguration processorConvertJobConfiguration;
    @Autowired
    private MultiThreadPagingConfiguration multiThreadPagingConfiguration;
    @Autowired
    private MultiThreadCursorConfiguration multiThreadCursorConfiguration;
    @Autowired
    private PartitionLocalConfiguration partitionLocalConfiguration;
    @Autowired
    private FileItemReadWriterJdbcJobConfiguration fileItemReadWriterJdbcJobConfiguration;


    // @Scheduled(fixedDelay = 1000)                                                    // scheduler 끝나는 시간 기준으로 1000 간격으로 실행
    // @Scheduled(fixedRate = 1000)                                                     // scheduler 시작하는 시간 기준으로 1000 간격으로 실행
    // @Scheduled(cron = "0 15 10 15 * ?")                                          // cron에 따라 실행
    // @Scheduled(cron = "0 15 10 15 * ?", zone = "Europe/Paris")   // cron에 TimeZone 설정 추가
    @Scheduled(initialDelay = 90000, fixedDelay = 90000)
    public void runJob() throws Exception {

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("reqSeq", System.currentTimeMillis()+"-Schedule");
        jobParametersBuilder.addString("createDate", "2021-07-14");
        jobParametersBuilder.addString("startDate", "2021-07-14");
        jobParametersBuilder.addString("endDate", "2021-07-14");

        try {
            log.info("--------------------- schedule execute ------------");
            jobLauncher.run(simpleJobConfiguration.simpleJob(), jobParametersBuilder.toJobParameters());
            jobLauncher.run(stepNextJobConfiguration.stepNextJob() ,jobParametersBuilder.toJobParameters());
            jobLauncher.run(stepNextConditionalJobConfiguration.stepNextConditionalJob(), jobParametersBuilder.toJobParameters());
            jobLauncher.run(deciderJobConfiguration.deciderJob(), jobParametersBuilder.toJobParameters());
            jobLauncher.run(jdbcCursorItemReaderJobConfiguration.jdbcCursorItemReaderJob(), jobParametersBuilder.toJobParameters());
            jobLauncher.run(jdbcPagingItemReaderJobConfiguration.jdbcPagingItemReaderJob(), jobParametersBuilder.toJobParameters());
            jobLauncher.run(jpaPagingItemReaderJobConfiguration.jpaPagingItemReaderJob(), jobParametersBuilder.toJobParameters());
            jobLauncher.run(jdbcBatchItemWriterJobConfiguration.jdbcBatchItemWriterJob(), jobParametersBuilder.toJobParameters());
            jobLauncher.run(jpaItemWriterJobConfiguration.jpaItemWriterJob(), jobParametersBuilder.toJobParameters());
            jobLauncher.run(customItemWriterJobConfiguration.customItemWriterJob(), jobParametersBuilder.toJobParameters());
            jobLauncher.run(processorConvertJobConfiguration.processorConvertJob(), jobParametersBuilder.toJobParameters());
            jobLauncher.run(multiThreadPagingConfiguration.job(), jobParametersBuilder.toJobParameters());
            jobLauncher.run(multiThreadCursorConfiguration.job(), jobParametersBuilder.toJobParameters());
            jobLauncher.run(partitionLocalConfiguration.job(), jobParametersBuilder.toJobParameters());
            jobLauncher.run(fileItemReadWriterJdbcJobConfiguration.fileItemReadWriterJdbcJob(), jobParametersBuilder.toJobParameters());
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        }
    }

}