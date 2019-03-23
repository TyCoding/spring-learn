package cn.tycoding.repository;

import cn.tycoding.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-18
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDaoTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;

    //查询所有
    @Test
    public void testFindAll() {
        List<User> list = userDao.findAll();
        list.forEach(user -> {
            logger.info("user={}", user);
        });
    }

    //根据ID查询
    @Test
    public void testFindById() {
//        User user = userDao.getOne(1L); //Error: org.hibernate.LazyInitializationException - no Session
        User user = userDao.findById(1L).get();
        logger.info("user={}", user);
    }

    //动态查询。根据某个字段查询
    @Test
    public void testFindByExample() {
        User user = new User();
        Example<User> example = Example.of(user);
        user.setUsername("tycoding");
        List<User> list = userDao.findAll(example);
        list.forEach(u -> {
            logger.info("user={}", u);
        });
    }

    //新增
    @Test
    public void testSave() {
        User user = new User();
        user.setUsername("测试");
        user.setPassword("测试");
        userDao.save(user);
        List<User> list = userDao.findAll();
        list.forEach(u -> {
            logger.info("user={}", u);
        });
    }

    //更新
    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(1L);
        user.setUsername("涂陌呀");
        userDao.save(user);
        logger.info("user={}", userDao.findById(user.getId()).get());
    }

    //删除
    @Test
    public void testDelete() {
        User user = new User();
        user.setId(4L);
        userDao.delete(user);
//        userDao.deleteById(4L);
    }

    //分页查询
    @Test
    public void testFindByPage() {
        int pageCode = 0; //当前页，从0开始
        int pageSize = 3; //每页显示10条记录
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(pageCode, pageSize, sort);
        Page<User> page = userDao.findAll(pageable);
        logger.info("总记录数={}", page.getTotalElements());
        logger.info("总页数={}", page.getTotalPages());
        logger.info("记录={}", page.getContent());
    }

    //自定义SQL
    @Test
    public void testFindByPassword() {
        User user = userDao.findByPassword("123");
        logger.info("user={}", user);
    }

    @Test
    public void testDeleteByPassword() {
        userDao.deleteByPassword("123");
        User user = userDao.findByPassword("123");
        logger.info("user={}", user);
    }

}