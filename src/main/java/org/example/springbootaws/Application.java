package org.example.springbootaws;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// @SpringBootApplication must always be at the top of the project
// since all the settings are read from here
// @EnableJpaAuditing -> create JpaConfig class separately
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
