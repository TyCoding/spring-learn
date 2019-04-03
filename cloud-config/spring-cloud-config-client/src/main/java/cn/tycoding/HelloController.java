package cn.tycoding;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tycoding
 * @date 2019-04-03
 */
@RestController
public class HelloController {

    @Value("${config.hello}")
    private String hello;

    @GetMapping("/hello")
    public String hello() {
        return this.hello;
    }
}
