package com.example.student_registration;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
public class StudentRegistrationApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(StudentRegistrationApplication.class, args);
    }
}