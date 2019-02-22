package cn.tycoding.mapper;

import cn.tycoding.entity.User;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-22
 */
public interface UserMapperXML {

    List<User> findAll();

    User findById(Long id);

    void save(User user);

    void update(User user);

    void delete(Long id);
}
