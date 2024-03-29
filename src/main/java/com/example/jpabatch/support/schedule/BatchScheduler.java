package com.example.jpabatch.support.schedule;

import com.example.jpabatch.sample.job.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final SimpleJobConfiguration simpleJobConfiguration;
    private final StepNextJobConfiguration stepNextJobConfiguration;
    private final StepNextConditionalJobConfiguration stepNextConditionalJobConfiguration;
    private final DeciderJobConfiguration deciderJobConfiguration;
    private final JdbcCursorItemReaderJobConfiguration jdbcCursorItemReaderJobConfiguration;
    private final JdbcPagingItemReaderJobConfiguration jdbcPagingItemReaderJobConfiguration;
    private final JpaPagingItemReaderJobConfiguration jpaPagingItemReaderJobConfiguration;
    private final JdbcBatchItemWriterJobConfiguration jdbcBatchItemWriterJobConfiguration;
    private final JpaItemWriterJobConfiguration jpaItemWriterJobConfiguration;
    private final CustomItemWriterJobConfiguration customItemWriterJobConfiguration;
    private final ProcessorConvertJobConfiguration processorConvertJobConfiguration;
    private final MultiThreadPagingConfiguration multiThreadPagingConfiguration;
    private final MultiThreadCursorConfiguration multiThreadCursorConfiguration;
    private final PartitionLocalConfiguration partitionLocalConfiguration;
    private final FileItemReadWriterJdbcJobConfiguration fileItemReadWriterJdbcJobConfiguration;


    // @Scheduled(fixedDelay = 1000)                                                    // scheduler 끝나는 시간 기준으로 1000 간격으로 실행
    // @Scheduled(fixedRate = 1000)                                                     // scheduler 시작하는 시간 기준으로 1000 간격으로 실행
    // @Scheduled(cron = "0 15 10 15 * ?")                           // cron 에 따라 실행
    // @Scheduled(cron = "0 15 10 15 * ?", zone = "Europe/Paris")   // cron 에 TimeZone 설정 추가
    @Scheduled(cron = "0 1 * * * ?")
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