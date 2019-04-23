package cn.tycoding.service;

import cn.tycoding.entity.SysUser;

/**
 * @author tycoding
 * @date 2019-04-23
 */
public interface UserService {

    /**
     * 根据用户名查询
     *
     * @param username
     * @return
     */
    SysUser findByUsername(String username);
}
