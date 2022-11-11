package com.spring.junit.exception.SpringJunitException;

import com.spring.junit.exception.SpringJunitException.model.Movie;
import com.spring.junit.exception.SpringJunitException.repository.MovieRepository;
import org.bson.types.ObjectId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringJunitExceptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJunitExceptionApplication.class, args);
	}

	@Bean
	public CommandLineRunner setup(MovieRepository repository){
		return (args) -> {
			repository.save(new Movie(new ObjectId("636bab81c33b70379d775036"),"Movie", "2022"));
			repository.save(new Movie(new ObjectId("636bab81c33b70379d775037"),"abcd","2018"));
		};
	}
}
