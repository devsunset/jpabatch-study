package com.example.jpabatch.sample.job;

import javax.persistence.EntityManagerFactory;

import com.example.jpabatch.sample.domain.Pay;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ProcessorConvertJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory emf;

    private static final int chunkSize = 10;

    @Bean
    public Job processorConvertJob() {
        return jobBuilderFactory.get("processorConvertJob")
                .preventRestart()
                .start(processorConvertJobStep())
                .build();
    }

    @Bean
    @JobScope
    public Step processorConvertJobStep() {
        return stepBuilderFactory.get( "processorConvertStep")
                .<Pay, String>chunk(chunkSize)
                .reader(processorConvertReader())
                .processor(processorConvertProcessor())
                .writer(processorConvertWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Pay> processorConvertReader() {
        return new JpaPagingItemReaderBuilder<Pay>()
                .name("processorConvertReader")
                .entityManagerFactory(emf)
                .pageSize(chunkSize)
                .queryString("SELECT p FROM Pay p")
                .build();
    }
    
    @Bean
    public ItemProcessor<Pay, String> processorConvertProcessor() {
        return pay -> {
            return pay.getTxName();
        };
    }

    private ItemWriter<String>  processorConvertWriter() {
        return items -> {
            for (String item : items) {
                log.info("Pay txName={}", item);
            }
        };
    }
}