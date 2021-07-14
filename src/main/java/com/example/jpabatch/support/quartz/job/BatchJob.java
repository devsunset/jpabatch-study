package com.example.jpabatch.support.quartz.job;

import com.example.jpabatch.sample.job.CustomItemWriterJobConfiguration;
import com.example.jpabatch.sample.job.DeciderJobConfiguration;
import com.example.jpabatch.sample.job.JdbcBatchItemWriterJobConfiguration;
import com.example.jpabatch.sample.job.JdbcCursorItemReaderJobConfiguration;
import com.example.jpabatch.sample.job.JdbcPagingItemReaderJobConfiguration;
import com.example.jpabatch.sample.job.JpaItemWriterJobConfiguration;
import com.example.jpabatch.sample.job.JpaPagingItemReaderJobConfiguration;
import com.example.jpabatch.sample.job.MultiThreadCursorConfiguration;
import com.example.jpabatch.sample.job.MultiThreadPagingConfiguration;
import com.example.jpabatch.sample.job.ProcessorConvertJobConfiguration;
import com.example.jpabatch.sample.job.SimpleJobConfiguration;
import com.example.jpabatch.sample.job.StepNextConditionalJobConfiguration;
import com.example.jpabatch.sample.job.StepNextJobConfiguration;

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
public class BatchJob extends QuartzJobBean implements InterruptableJob {

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
    
	@Override
	public void interrupt() throws UnableToInterruptJobException {
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
	
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("reqSeq", System.currentTimeMillis()+"-Quartz");
        jobParametersBuilder.addString("createDate", "2021-07-14");

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
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        }
	}

}
