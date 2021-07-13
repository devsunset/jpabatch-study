package com.example.jpabatch.sample.job;

import javax.persistence.EntityManagerFactory;

import com.example.jpabatch.sample.domain.Student;
import com.example.jpabatch.sample.domain.Teacher;
import com.example.jpabatch.sample.repository.TeacherRepository;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
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
    private final TeacherRepository teacherRepository;
    private final EntityManagerFactory emf;

    private static final int chunkSize = 10;

    @Bean
    public Job processorConvertJob() {
        return jobBuilderFactory.get("processorConvertJob")
                .preventRestart()
                .start(initDataStep())
                .next(processorConvertJobStep())
                .build();
    }

    @Bean
    @JobScope
    public Step initDataStep() {
        log.info(" INIT DATA =============================================");
        return stepBuilderFactory.get("initDataStep")
                .tasklet((contribution, chunkContext) -> {

                    teacherRepository.deleteAll();
                    
                    Teacher teacher1=  new Teacher("Teacher1", "java");
                    teacher1.addStudent(new Student("abc1"));

                    Teacher teacher2=  new Teacher("Teacher2", "html");
                    teacher2.addStudent(new Student("abc2"));

                    Teacher teacher3=  new Teacher("Teacher3", "css");
                    teacher3.addStudent(new Student("abc3"));

                    Teacher teacher4=  new Teacher("Teacher4", "javascript");
                    teacher4.addStudent(new Student("abc4"));

                    teacherRepository.save(teacher1);
                    teacherRepository.save(teacher2);
                    teacherRepository.save(teacher3);
                    teacherRepository.save(teacher4);
                    return RepeatStatus.FINISHED;
                })
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



    @Bean
    public ItemProcessor<Teacher, String> processorConvertProcessor() {
        return Teacher -> {
            boolean isIgnoreTarget = Teacher.getId() % 2 == 0L;
            if(isIgnoreTarget){
                log.info(">>>>>>>>> Teacher name={}, isIgnoreTarget={}", Teacher.getName(), isIgnoreTarget);
                //    Spring Batch에서 트랜잭션 범위는 Chunk단위 입니다.
                //    그래서 Reader에서 Entity를 반환해주었다면 Entity간의 Lazy Loading이 가능합니다. 
                //    이는 Processor뿐만 아니라 Writer에서도 가능 합니다.
                //    Processor와 Writer는 트랜잭션 범위 안이며, Lazy Loading이 가능
                log.info(">>>>>>>>> Teacher.getStudents={}", ((Student)(Teacher.getStudents().get(0))).getName());
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