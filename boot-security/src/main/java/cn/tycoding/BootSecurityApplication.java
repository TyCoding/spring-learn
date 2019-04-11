package cn.tycoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class BootSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootSecurityApplication.class, args);
    }

}
