package cn.tycoding.mapper;

import cn.tycoding.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-22
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperAnoTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapperAno userMapperAno;

    @Test
    public void testFindAll() {
        List<User> list = userMapperAno.findAll();
        list.forEach(user -> {
            logger.info("user={}", user);
        });
    }

    @Test
    public void testFindById(){
        logger.info("user={}", userMapperAno.findById(1L));
    }

    @Test
    public void testSave(){
        User user = new User();
        user.setUsername("测试");
        user.setPassword("123");
        user.setCreateTime(new Date());
        userMapperAno.save(user);
        testFindAll();
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(4L);
        user.setUsername("测试呀");
        userMapperAno.update(user);
        testFindAll();
    }

    @Test
    public void delete() {
        userMapperAno.delete(3L);
        testFindAll();
    }
}