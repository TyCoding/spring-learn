package cn.tycoding.mapper;


import cn.tycoding.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-23
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findAll() {
        List<User> list = userMapper.selectAll();
        list.forEach(user -> {
            logger.info("user={}", user);
        });
    }

    @Test
    public void findById() {
        User user = userMapper.selectByPrimaryKey(1L);
        logger.info("user={}", user);
    }

    @Test
    public void save() {
        User user = new User();
        user.setUsername("涂陌");
        user.setPassword("123");
        user.setAge(20);
        userMapper.insert(user);
        findAll();
    }

    @Test
    public void update() {
        User user = new User();
        user.setId(2L);
        user.setUsername("小涂陌");
        userMapper.updateByPrimaryKey(user);
        findAll();
    }

    @Test
    public void delete() {
        userMapper.deleteByPrimaryKey(2L);
        findAll();
    }
}