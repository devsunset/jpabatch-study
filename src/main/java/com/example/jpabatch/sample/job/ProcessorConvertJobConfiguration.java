package com.example.jpabatch.sample.job;

import javax.persistence.EntityManagerFactory;

import com.example.jpabatch.sample.domain.Teacher;

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
                .<Teacher, String>chunk(chunkSize)
                .reader(processorConvertReader())
                .processor(processorConvertProcessor())
                // .processor(compositeProcessor())
                .writer(processorConvertWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Teacher> processorConvertReader() {
        return new JpaPagingItemReaderBuilder<Teacher>()
                .name("processorConvertReader")
                .entityManagerFactory(emf)
                .pageSize(chunkSize)
                .queryString("SELECT p FROM Teacher p")
                .build();
    }

    //     Spring Batch에서 트랜잭션 범위는 Chunk단위 입니다.
    //    그래서 Reader에서 Entity를 반환해주었다면 Entity간의 Lazy Loading이 가능합니다. 
    //    이는 Processor뿐만 아니라 Writer에서도 가능 합니다.
    //    Processor와 Writer는 트랜잭션 범위 안이며, Lazy Loading이 가능

    @Bean
    public ItemProcessor<Teacher, String> processorConvertProcessor() {
        return Teacher -> {
            boolean isIgnoreTarget = Teacher.getId() % 2 == 0L;
            if(isIgnoreTarget){
                log.info(">>>>>>>>> Teacher name={}, isIgnoreTarget={}", Teacher.getName(), isIgnoreTarget);
                return null; //Filter  -> null return skip
            }
      
            return Teacher.getName();
        };
    }

    // @Bean
    // @SuppressWarnings("uncheckd")
    // public CompositeItemProcessor compositeProcessor() {
    //     List<ItemProcessor> delegates = new ArrayList<>(2);
    //     delegates.add(processor1());
    //     delegates.add(processor2());
    //     CompositeItemProcessor processor = new CompositeItemProcessor<>();
    //     processor.setDelegates(delegates);
    //     return processor;
    // }

    // public ItemProcessor<Teacher, String> processor1() {
    //     return Teacher::getName;
    // }

    // public ItemProcessor<String, String> processor2() {
    //     return name -> "안녕하세요. "+ name + " 입니다.";
    // }

    private ItemWriter<String>  processorConvertWriter() {
        return items -> {
            for (String item : items) {
                log.info("Teacher txName={}", item);
            }
        };
    }
}