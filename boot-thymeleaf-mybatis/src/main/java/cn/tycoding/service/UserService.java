package cn.tycoding.service;

import cn.tycoding.entity.User;
import cn.tycoding.entity.UserWithRole;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-24
 */
public interface UserService {

    User findByName(String username);

    UserWithRole findById(Long id);

    List<User> queryList(User user);

    void add(UserWithRole user);

    boolean checkName(String name, String id);

    void update(UserWithRole user);

    void delete(List<Long> keys);
}
