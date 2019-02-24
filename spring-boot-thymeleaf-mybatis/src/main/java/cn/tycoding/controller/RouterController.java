package cn.tycoding.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author tycoding
 * @date 2019-02-24
 */
@Controller
public class RouterController {

    @GetMapping("/")
    public String index() {
        return "/user/user";
    }

    @GetMapping("/user")
    public String user() {
        return "/user/user";
    }
}
