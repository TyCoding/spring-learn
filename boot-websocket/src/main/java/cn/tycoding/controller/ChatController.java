package cn.tycoding.controller;

import cn.tycoding.config.WebsocketServerEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author tycoding
 * @date 2019-06-11
 */
@Controller
@RequestMapping("/chat")
public class ChatController {

    @GetMapping("/target")
    public String target() {
        return "/target";
    }

    @ResponseBody
    @GetMapping("/push/{id}/{message}")
    public String push(@PathVariable("id") String id, @PathVariable("message") String message) {
        try {
            WebsocketServerEndpoint.sendInfo(id, message);
            return "推送消息成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "推送消息失败";
        }
    }
}
