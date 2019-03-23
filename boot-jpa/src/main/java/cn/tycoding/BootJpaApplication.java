package cn.tycoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BootJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootJpaApplication.class, args);
    }

}
