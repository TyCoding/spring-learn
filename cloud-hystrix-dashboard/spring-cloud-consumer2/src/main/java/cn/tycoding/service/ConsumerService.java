package cn.tycoding.service;

import cn.tycoding.hystrix.ConsumerHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author tycoding
 * @date 2019-03-22
 */
@FeignClient(name = "spring-cloud-producer2", fallback = ConsumerHystrix.class)
public interface ConsumerService {

    @GetMapping("/producer2/{name}")
    String hello2(@PathVariable("name") String name);
}
