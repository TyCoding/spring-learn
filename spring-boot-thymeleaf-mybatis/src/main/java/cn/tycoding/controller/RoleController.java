package cn.tycoding.controller;

import cn.tycoding.dto.QueryPage;
import cn.tycoding.dto.ResponseCode;
import cn.tycoding.entity.Role;
import cn.tycoding.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tycoding
 * @date 2019-02-24
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/list")
    public ResponseCode queryList(QueryPage queryPage, Role role) {
        return ResponseCode.success(super.selectByPageNumSize(queryPage, () -> roleService.queryList(role)));
    }
}
