package cn.tycoding.service.impl;

import cn.tycoding.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * @author tycoding
 * @date 2019-02-25
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisServiceImplTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisService redisService;

    @Test
    public void getKeys() {
//        Set<String> keys = stringRedisTemplate.keys("*");
//        logger.info("keys={}", keys);
//        Set<String> keys1 = redisTemplate.keys("*");
//        logger.info("keys1={}", keys1);

        Set<String> keys = redisService.getKeys("*");
        logger.info("keys={}", keys);
    }

    @Test
    public void set() {
        Boolean set = redisService.set("new_key", "new_value");
        logger.info("是否新增={}", set);
        getKeys();
    }

    @Test
    public void get() {
        String key = redisService.get("new_key");
        logger.info("key={}", key);
    }

    @Test
    public void del() {
        redisService.set("new_key2", "new_value2");
        redisService.set("new_key3", "new_value3");
        String[] keys = {"new_key2", "new_key3"};
        Long count = redisService.del(keys);
        logger.info("删除的个数={}", count);
        getKeys();
    }

    @Test
    public void exists() {
        String[] keys = {"new_key"};
        Long exists = redisService.exists(keys);
        logger.info("是否存在={}", exists);
    }

    @Test
    public void pttl() {
        Long time = redisService.pttl("new_key");
        logger.info("剩余过期时间=>{}", time);
    }

    @Test
    public void pexpire() {
        Long result = redisService.pexpire("new_key", 10000000000L);
        logger.info("是否设置成功={}", result);
        pttl();
    }
}