package com.dev_course_assignment1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DevCourseAssignment1Application {

    public static void main(String[] args) {
        SpringApplication.run(DevCourseAssignment1Application.class, args);
    }

}
