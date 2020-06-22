package cn.tycoding.start.nacos.consumer.controller;

import cn.tycoding.start.nacos.consumer.feign.NacosProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tycoding
 * @date 2020/6/22
 */
@RestController
public class NacosConsumerController {

    @Autowired
    private NacosProviderService nacosProviderService;

    /**
     * 直接调用Feign提供的接口
     * providerService(`consumer`服务) -> feign ->  providerController(`provider`服务)
     *
     * @return
     */
    @GetMapping("/start")
    public String start() {
        return nacosProviderService.start("tycoding");
    }

    @GetMapping("/test")
    public String router() {
        return "this from `nacos-consumer` services, request path: /router-path-test";
    }
}
