package cn.tycoding.repository;

import cn.tycoding.entity.Demo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-22
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoDaoTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DemoDao demoDao;

    //查询所有
    @Test
    public void testFindAll() {
        List<Demo> list = demoDao.findAll();
        list.forEach(demo -> {
            logger.info("demo={}", demo);
        });
    }

    //根据ID查询
    @Test
    public void testFindById() {
//        Demo demo = demoDao.getOne(1L); //Error: org.hibernate.LazyInitializationException - no Session
        Demo demo = demoDao.findById(1L).get();
        logger.info("demo={}", demo);
    }

    //动态查询。根据某个字段查询
    @Test
    public void testFindByExample() {
        Demo demo = new Demo();
        Example<Demo> example = Example.of(demo);
        demo.setName("tycoding");
        List<Demo> list = demoDao.findAll(example);
        list.forEach(d -> {
            logger.info("demo={}", d);
        });
    }

    @Test
    public void testSave() {
        Demo demo = new Demo();
        demo.setName("测试");
        demo.setCreateTime(new Date());
        demoDao.save(demo);
        List<Demo> list = demoDao.findAll();
        list.forEach(d -> {
            logger.info("demo={}", d);
        });
    }

    @Test
    public void testUpdate() {
        Demo demo = new Demo();
        demo.setId(2L);
        demo.setName("涂陌呀");
        demoDao.save(demo);
        logger.info("demo={}", demoDao.findById(demo.getId()).get());
    }

    @Test
    public void testDelete() {
        Demo demo = new Demo();
        demo.setId(2L);
        demoDao.delete(demo);
//        demoDao.deleteById(1L);
    }

    @Test
    public void testFindByPage() {
        int pageCode = 1; //当前页
        int pageSize = 3; //每页显示10条记录
//        Sort sort = new Sort(Sort.Direction.ASC, "id");
//        Pageable pageable = new PageRequest(pageCode, pageSize, sort);
        Pageable pageable = new PageRequest(pageCode, pageSize);
        Page<Demo> page = demoDao.findAll(pageable);
        logger.info("总记录数={}", page.getTotalElements());
        logger.info("总页数={}", page.getTotalPages());
        logger.info("记录={}", page.getContent());
    }
}