package cn.tycoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author tycoding
 * @date 2019-06-11
 */
@SpringBootApplication
public class BootWebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootWebSocketApplication.class, args);
    }

    @GetMapping
    public String index() {
        return "/index";
    }
}
