package kr.co._29cm.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EnableJpaRepositories("kr.co._29cm.homework.repository")
@SpringBootApplication
public class HomeworkApplication {
    public static void main(String[] args) {
        SpringApplication.run(HomeworkApplication.class, args);
    }
}
