package cn.tycoding.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tycoding
 * @date 2019-03-22
 */
@RestController
public class ProducerController2 {

    @GetMapping("/producer/{name}")
    public String hello(@PathVariable("name") String name) {
        return "Hello " + name + " , this is producer2 message!";
    }
}
