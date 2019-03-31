package cn.tycoding.service;

import cn.tycoding.hystrix.ConsumerHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author tycoding
 * @date 2019-03-22
 */
@FeignClient(name = "spring-cloud-producer", fallback = ConsumerHystrix.class)
public interface ConsumerService {

    @GetMapping("/producer/{name}")
    String hello(@PathVariable("name") String name);
}
