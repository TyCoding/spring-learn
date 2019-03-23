package cn.tycoding.service.impl;

import cn.tycoding.dto.QueryPage;
import cn.tycoding.entity.Article;
import cn.tycoding.service.EsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author tycoding
 * @date 2019-03-20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EsServiceImplTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EsService esService;

    @Test
    public void add() {
        Article article = new Article();
        article.setId("1");
        article.setTitle("测试添加文章索引");
        article.setContent("这是测试数据");
        esService.add(article);
    }

    @Test
    public void search() {
        QueryPage page = new QueryPage();
        page.setCurrent(1);
        page.setSize(10);
        Page<Article> list = esService.search("测试", page);
        list.forEach(article -> {
            logger.info("article ==> {}", article);
        });
    }
}