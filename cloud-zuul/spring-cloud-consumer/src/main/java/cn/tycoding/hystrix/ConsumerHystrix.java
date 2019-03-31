package cn.tycoding.hystrix;

import cn.tycoding.service.ConsumerService;
import org.springframework.stereotype.Component;

/**
 * @author tycoding
 * @date 2019-03-31
 */
@Component
public class ConsumerHystrix implements ConsumerService {

    @Override
    public String hello(String name) {
        return "Hello " + name + ". this message from hystrix";
    }
}
