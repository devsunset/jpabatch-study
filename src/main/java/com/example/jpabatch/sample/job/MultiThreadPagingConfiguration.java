package com.example.jpabatch.sample.job;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import com.example.jpabatch.sample.domain.Product;
import com.example.jpabatch.sample.domain.ProductBackup;
import com.example.jpabatch.sample.domain.ProductStatus;
import com.example.jpabatch.sample.repository.ProductBackupRepository; 
import com.example.jpabatch.sample.repository.ProductRepository;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MultiThreadPagingConfiguration {
    public static final String JOB_NAME = "multiThreadPagingBatch";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final ProductRepository productRepository;
    private final ProductBackupRepository productBackupRepository;

    private int chunkSize;

    

    @Value("${chunkSize:10}")
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    private int poolSize;

    @Value("${poolSize:4}") 
    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    @Bean(name = JOB_NAME+"taskPool")
    public TaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor(); 
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(poolSize);
        executor.setThreadNamePrefix("multi-thread-");
        executor.setWaitForTasksToCompleteOnShutdown(Boolean.TRUE);
        executor.initialize();
        return executor;
    }

    @Bean(name = JOB_NAME)
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(initDataStep())
                .next(step())
                .preventRestart()
                .build();
    }

    @Bean(name = JOB_NAME +"_initData_step")
    @JobScope
    public Step initDataStep() {
        log.info("MultiThreadPagingConfiguration  INIT DATA =============================================");
        return stepBuilderFactory.get("initDataStep")
                .tasklet((contribution, chunkContext) -> {

                    productRepository.deleteAll();
                    productBackupRepository.deleteAll();

                    productRepository.save(new Product("name1", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );
                    productRepository.save(new Product("name2", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );
                    productRepository.save(new Product("name3", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );
                    productRepository.save(new Product("name4", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );
                    productRepository.save(new Product("name5", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );
                    productRepository.save(new Product("name6", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );
                    productRepository.save(new Product("name7", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );
                    productRepository.save(new Product("name8", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );
                    productRepository.save(new Product("name9", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );
                    productRepository.save(new Product("name10", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );
                    productRepository.save(new Product("name11", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );
                    productRepository.save(new Product("name12", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );
                    productRepository.save(new Product("name13", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );
                    productRepository.save(new Product("name14", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );
                    productRepository.save(new Product("name15", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );
                    productRepository.save(new Product("name16", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );
                    productRepository.save(new Product("name17", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );
                    productRepository.save(new Product("name18", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );
                    productRepository.save(new Product("name19", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );
                    productRepository.save(new Product("name20", 1234L, LocalDate.parse("2021-07-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")), ProductStatus.APPROVE) );

                    productRepository.flush();
                    
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    
    @Bean(name = JOB_NAME +"_step")
    @JobScope
    public Step step() {
        return stepBuilderFactory.get(JOB_NAME +"_step")
                .<Product, ProductBackup>chunk(chunkSize)
                .reader(reader(null))
                .processor(processor())
                .writer(writer())
                .taskExecutor(executor()) 
                .throttleLimit(poolSize) 
                .build();
    }

    @Bean(name = JOB_NAME +"_reader")
    @StepScope
    public JpaPagingItemReader<Product> reader(@Value("#{jobParameters[createDate]}") String createDate) {

        Map<String, Object> params = new HashMap<>();
        params.put("createDate", LocalDate.parse(createDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return new JpaPagingItemReaderBuilder<Product>()
                .name(JOB_NAME +"_reader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT p FROM Product p WHERE p.createDate =:createDate")
                .parameterValues(params)
                .saveState(false)
                .build();
    }

    private ItemProcessor<Product, ProductBackup> processor() {
        return ProductBackup::new;
    }

    @Bean(name = JOB_NAME +"_writer")
    @StepScope
    public JpaItemWriter<ProductBackup> writer() {
        log.info("-------------ProdcutBackup------------");
        return new JpaItemWriterBuilder<ProductBackup>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}