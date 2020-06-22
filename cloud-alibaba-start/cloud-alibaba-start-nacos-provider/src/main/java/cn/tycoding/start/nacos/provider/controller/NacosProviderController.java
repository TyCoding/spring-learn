package cn.tycoding.start.nacos.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tycoding
 * @date 2020/6/22
 */
@RestController
public class NacosProviderController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/start/{author}")
    public String start(@PathVariable(value = "author") String author) {
        return "Start Nacos, by " + author + ", this port is " + port;
    }
}
