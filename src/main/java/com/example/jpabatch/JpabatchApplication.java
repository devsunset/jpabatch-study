package com.example.jpabatch;

import com.example.jpabatch.sample.domain.Pay;
import com.example.jpabatch.sample.repository.PayRepository;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableBatchProcessing
public class JpabatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpabatchApplication.class, args);
	}

	@Bean
	public CommandLineRunner initPayData(PayRepository repository) {
		return (args) -> {
			repository.save(new Pay(1000L,"trade1", "2021-07-12 11:00:00"));
			repository.save(new Pay(2000L,"trade2", "2021-07-12 11:00:00"));
			repository.save(new Pay(3000L,"trade3", "2021-07-12 11:00:00"));
			repository.save(new Pay(4000L,"trade4", "2021-07-12 11:00:00"));
		};
	}
}
