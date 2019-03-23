package cn.tycoding.service;

import cn.tycoding.entity.User;

/**
 * @author tycoding
 * @date 2019-02-24
 */
public interface UserService {

    User findByUsername(String username);
}
