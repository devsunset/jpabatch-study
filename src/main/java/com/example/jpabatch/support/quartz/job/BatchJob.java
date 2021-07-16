package com.example.jpabatch.support.quartz.job;

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

import lombok.RequiredArgsConstructor;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class BatchJob extends QuartzJobBean implements InterruptableJob {

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
    
	@Override
	public void interrupt() throws UnableToInterruptJobException {
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
	
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("reqSeq", System.currentTimeMillis()+"-Quartz");
        jobParametersBuilder.addString("createDate", "2021-07-14");
        jobParametersBuilder.addString("startDate", "2021-07-14");
        jobParametersBuilder.addString("endDate", "2021-07-14");

        try {
            log.info("--------------------- batch execute ------------");
            jobLauncher.run(simpleJobConfiguration.simpleJob(), jobParametersBuilder.toJobParameters());
            jobLauncher.run(stepNextJobConfiguration.stepNextJob() ,jobParametersBuilder.toJobParameters());
            jobLauncher.run(stepNextConditionalJobConfiguration.stepNextConditionalJob(), jobParametersBuilder.toJobParameters());
            jobLauncher.run(deciderJobConfiguration.deciderJob(), jobParametersBuilder.toJobParameters());
            jobLauncher.run(jdbcCursorItemReaderJobConfiguration.jdbcCursorItemReaderJob(), jobParametersBuilder.toJobParameters());
            try {
                jobLauncher.run(jdbcPagingItemReaderJobConfiguration.jdbcPagingItemReaderJob(), jobParametersBuilder.toJobParameters());
            } catch (Exception e) {
                throw new JobExecutionException();
            }
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
