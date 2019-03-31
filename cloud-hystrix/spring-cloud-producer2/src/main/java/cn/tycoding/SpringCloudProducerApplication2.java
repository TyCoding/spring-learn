package cn.tycoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudProducerApplication2 {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudProducerApplication2.class, args);
    }

}
