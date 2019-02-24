package cn.tycoding.service.impl;

import cn.tycoding.entity.User;
import cn.tycoding.entity.UserRole;
import cn.tycoding.entity.UserWithRole;
import cn.tycoding.mapper.UserMapper;
import cn.tycoding.mapper.UserRoleMapper;
import cn.tycoding.service.UserRoleService;
import cn.tycoding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tycoding
 * @date 2019-02-24
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public User findByName(String username) {
        Example example = new Example(User.class);
        example.createCriteria().andCondition("username=", username);
        List<User> list = this.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public UserWithRole findById(Long id) {
        List<UserWithRole> list = userMapper.findById(id);
        if (list.isEmpty()) {
            return null;
        }
        List<Long> roleIds = list.stream().map(UserWithRole::getRoleId).collect(Collectors.toList());
        UserWithRole userWithRole = list.get(0);
        userWithRole.setRoleIds(roleIds);
        return userWithRole;
    }

    @Override
    public List<User> queryList(User user) {
        try {
            return userMapper.queryList(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void add(UserWithRole user) {
        this.save(user);
        saveUserRole(user);
    }

    @Override
    public boolean checkName(String name, String id) {
        if (name.isEmpty()) {
            return false;
        }
        Example example = new Example(User.class);
        if (!id.isEmpty()) {
            example.createCriteria().andCondition("lower(username)=", name.toLowerCase()).andNotEqualTo("id", id);
        } else {
            example.createCriteria().andCondition("lower(username)=", name.toLowerCase());
        }
        List<User> users = this.selectByExample(example);
        if (users.size() > 0) {
            return false;
        }
        return true;
    }

    private void saveUserRole(UserWithRole user) {
        user.getRoleIds().forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        });
    }

    @Override
    @Transactional
    public void update(UserWithRole user) {
        user.setPassword(null);
        this.updateNotNull(user);
        Example example = new Example(UserRole.class);
        example.createCriteria().andCondition("user_id=", user.getId());
        userRoleMapper.deleteByExample(example);
        saveUserRole(user);
    }

    @Override
    public void delete(List<Long> keys) {
        this.batchDelete(keys, "id", User.class);
        userRoleService.deleteUserRolesByUserId(keys);
    }

}
