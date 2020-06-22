package cn.tycoding.start.nacos.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 演示Nacos配置中心客户端端
 *
 * @author tycoding
 * @date 2020/6/22
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigApplication.class, args);
    }
}
