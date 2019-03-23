package cn.tycoding.controller;

import cn.tycoding.dto.QueryPage;
import cn.tycoding.dto.ResponseCode;
import cn.tycoding.entity.User;
import cn.tycoding.entity.UserWithRole;
import cn.tycoding.enums.StatusEnums;
import cn.tycoding.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-24
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @PostMapping("/list")
    public ResponseCode queryList(QueryPage queryPage, User user) {
        return ResponseCode.success(super.selectByPageNumSize(queryPage, () -> userService.queryList(user)));
    }

    @GetMapping("findById")
    public ResponseCode findById(Long id) {
        return ResponseCode.success(userService.findById(id));
    }

    @PostMapping("/add")
    public ResponseCode add(@RequestBody UserWithRole user) {
        try {
            userService.add(user);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.error();
        }
    }

    @GetMapping("/checkName")
    public ResponseCode checkName(String name, String id) {
        if (name.isEmpty()) {
            return new ResponseCode(StatusEnums.PARAM_ERROR);
        }
        if (!userService.checkName(name, id)) {
            return new ResponseCode(StatusEnums.PARAM_REPEAT);
        }
        return ResponseCode.success();
    }

    @PostMapping("/update")
    public ResponseCode update(@RequestBody UserWithRole user) {
        try {
            userService.update(user);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.error();
        }
    }

    @PostMapping("/delete")
    public ResponseCode delete(@RequestBody List<Long> ids) {
        try {
            userService.delete(ids);
            return ResponseCode.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.error();
        }
    }
}
