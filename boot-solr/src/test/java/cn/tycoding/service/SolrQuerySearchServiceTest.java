package cn.tycoding.service;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tycoding
 * @date 2019-03-06
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SolrQuerySearchServiceTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //1. 注入SolrClient类
    @Autowired
    private SolrClient solrClient;

    @Test
    public void search() {
        //2. 构建查询类
        SolrQuery query = new SolrQuery();

        //3. 封装查询条件，在solr中对应: localhost:8983/solr/new_core/select?q=keyword:*:*
        query.set("q", "keyword:*");

        try {
            //4. 查询，获取response响应数据
            QueryResponse response = solrClient.query(query);

            //5. 从response中获取查询结果
            SolrDocumentList documents = response.getResults();

            //6. 打印查询结果
            documents.forEach(document -> {
                logger.info("id={} --> username={}", document.getFieldValue("id"), document.getFieldValue("username"));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
