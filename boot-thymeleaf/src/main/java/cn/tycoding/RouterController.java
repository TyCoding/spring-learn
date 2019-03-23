package cn.tycoding;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-20
 */
@Controller
public class RouterController {

    @GetMapping("/")
    public String main() {
        return "/common/main";
    }

    @RequestMapping("/index")
    public String index(Model model) {

        model.addAttribute("test", "测试");


        User user = new User();
        user.setUsername("tycoding");
        user.setPassword("123");
        model.addAttribute("user", user);


        List<String> list = new ArrayList<>();
        list.add("循环1");
        list.add("循环2");
        list.add("循环3");
        model.addAttribute("list", list);

        return "index";
    }
}
