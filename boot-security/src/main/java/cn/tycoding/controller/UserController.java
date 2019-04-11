package cn.tycoding.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author tycoding
 * @date 2019-04-09
 */
@Slf4j
@Controller
public class UserController {

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Hello I'm TyCoding, this is Login test";
    }

    /**
     * 页面路由，当使用GET请求访问/login接口，会自动跳转到`/templates/login.html`页面
     *
     * @return login登录页面路由地址
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 页面路由，当使用GET请求访问/login接口，会自动跳转到`/templates/index.html`页面
     *
     * @return index首页面路由地址
     */
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * 获取当前登录用户信息
     *
     * @param authentication 封装了当前登录用户信息的Authentication对象
     * @return 用户信息集合对象
     */
    @GetMapping("/info")
    @ResponseBody
    public Object getCurrentUser(Authentication authentication) {
        return authentication;
    }
}
