package cn.tycoding.mapper;

import cn.tycoding.entity.User;
import cn.tycoding.entity.UserWithRole;
import cn.tycoding.utils.MyMapper;

import java.util.List;

public interface UserMapper extends MyMapper<User> {
    
    List<UserWithRole> findById(Long id);

    List<User> queryList(User user);
}