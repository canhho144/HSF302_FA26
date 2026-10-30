package com.hsf302.student; // Package của file main

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hsf302.student", "com.example.studentthymeleaf"})
@EnableJpaRepositories(basePackages = "com.hsf302.student.repository")

public class StudentThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentThymeleafApplication.class, args);
	}
}