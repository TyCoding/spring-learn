package cn.tycoding.service.impl;

import cn.tycoding.entity.Comments;
import cn.tycoding.service.MongoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tycoding
 * @date 2019-03-19
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MongoServiceImplTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MongoService mongoService;

    @Test
    public void findAll() {
        List<Comments> list = mongoService.findAll();
        list.forEach(comments -> {
            logger.info("comments ==> {}", comments);
        });
    }

    @Test
    public void findById() {
        Comments comments = mongoService.findById(1L);
        logger.info("comments ==> {}", comments);
    }

    @Test
    public void add() {
        Comments comments = new Comments();
        comments.setContent("我是测试类");
        comments.setCreateTime(new Date());
        comments.setUsername("tycoding");
        comments.setVisits(12L);
        mongoService.add(comments);
        this.findAll();
    }

    @Test
    public void update() {
        Comments comments = new Comments();
        comments.set_id(1L);
        comments.setUsername("我是修改的");
        mongoService.update(comments);
        this.findAll();
    }

    @Test
    public void delete() {
        List<Long> ids = new ArrayList<>();
        ids.set(1, 2L);
        mongoService.delete(ids);
        this.findAll();
    }
}