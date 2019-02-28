package cn.tycoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("cn.tycoding.mapper")
public class SpringBootThymeleafMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootThymeleafMybatisApplication.class, args);
    }

}
