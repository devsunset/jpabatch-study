package com.example.jpabatch.sample.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SimpleJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory; 
    private final StepBuilderFactory stepBuilderFactory;

    private final SimpleJobTasklet simpleJobTasklet;
    
    @Bean
    public Job simpleJob() {
        log.info("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ : SimpleJobConfiguration");
        return jobBuilderFactory.get("simpleJob")
                .start(simpleStep1(null))
                .next(simpleStep2(null))
                .next(simpleStep3())
                .build();
    }

    @Bean
    @JobScope
    public Step simpleStep1(@Value("#{jobParameters[reqSeq]}") String reqSeq) {
        log.info("============================================= : "+reqSeq);
        return stepBuilderFactory.get("simpleStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("========== This is Step1");
                    log.info("========== reqSeq = {}", reqSeq);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step simpleStep2(@Value("#{jobParameters[reqSeq]}") String reqSeq) {
        log.info("============================================= : "+reqSeq);
        return stepBuilderFactory.get("simpleStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("========== This is Step2");
                    log.info("========== reqSeq = {}", reqSeq);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step simpleStep3() {
        return stepBuilderFactory.get("simpleStep3")
                .tasklet(simpleJobTasklet)
                .build();
    }

    @Component
    @StepScope
    public class SimpleJobTasklet implements Tasklet {
        
        @Value("#{jobParameters[reqSeq]}") 
        private String reqSeq;

        public SimpleJobTasklet(){
            log.info("========== tasklet generate");
        }

        @Override
        public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
            log.info("========== This is Step3");
            log.info("========== reqSeq = {}", reqSeq);
            return null;
        }

    }
}