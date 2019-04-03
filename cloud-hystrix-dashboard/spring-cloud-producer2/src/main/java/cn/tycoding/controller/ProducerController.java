package cn.tycoding.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tycoding
 * @date 2019-03-22
 */
@RestController
public class ProducerController {

    @GetMapping("/producer2/{name}")
    public String hello2(@PathVariable("name") String name) {
        return "Hello " + name + " , this is producer2 message!";
    }
}
