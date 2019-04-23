package cn.tycoding.service.impl;

import cn.tycoding.entity.SysUser;
import cn.tycoding.mapper.UserMapper;
import cn.tycoding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tycoding
 * @date 2019-04-23
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public SysUser findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
