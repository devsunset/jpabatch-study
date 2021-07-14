package com.example.jpabatch.sample.job;

import javax.sql.DataSource;

import com.example.jpabatch.sample.domain.Person;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class FileItemReadWriterJdbcJobConfiguration {
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private static final int chunkSize = 10;
  private final DataSource dataSource;

  @Bean
  public Job fileItemReadWriterJdbcJob() {
    return jobBuilderFactory.get("fileItemReadWriterJdbcJob").start(fileItemReadWriterJdbcStep()).build();
  }

  @Bean
  public Step fileItemReadWriterJdbcStep() {
    return stepBuilderFactory.get("fileItemReadWriterJdbcStep").<Person, Person>chunk(chunkSize)
        .reader(fileItemReadReader()).processor(processorConvert()).writer(jdbcBatchWriter()).build();
  }

  @Bean
  public FlatFileItemReader<Person> fileItemReadReader() {
    return new FlatFileItemReaderBuilder<Person>().name("personItemReader")
        .resource(new ClassPathResource("sample-data.csv")).delimited().names(new String[] { "firstName", "lastName" })
        .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {
          {
            setTargetType(Person.class);
          }
        }).build();
  }

  @Bean
  public ItemProcessor<Person, Person> processorConvert() {
    return Person -> {
      final String firstName = Person.getFirstName().toUpperCase();
      final String lastName = Person.getLastName().toUpperCase();
      final Person transformedPerson = new Person(firstName, lastName);
      log.info("Converting (" + Person + ") into (" + transformedPerson + ")");
      return transformedPerson;
    };
  }

  @Bean
  public JdbcBatchItemWriter<Person> jdbcBatchWriter() {
    return new JdbcBatchItemWriterBuilder<Person>().dataSource(dataSource)
        .sql("INSERT INTO person (first_name, last_name) VALUES (:firstName, :lastName)").beanMapped().build();
  }
}