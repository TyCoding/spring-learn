package cn.tycoding.start.nacos.config.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tycoding
 * @date 2020/6/22
 */
@RestController
public class ConfigController {

    // 从Spring上下文环境中获取配置信息，保证配置实时更新
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @GetMapping("/config")
    public String config() {
        return applicationContext.getEnvironment().getProperty("server.port")
                + "， author: "
                + applicationContext.getEnvironment().getProperty("author");
    }
}
