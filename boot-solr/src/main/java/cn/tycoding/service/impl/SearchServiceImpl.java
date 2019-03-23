package cn.tycoding.service.impl;

import cn.tycoding.entity.Search;
import cn.tycoding.service.SearchService;
import cn.tycoding.utils.ResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tycoding
 * @date 2019-03-03
 */
@Service
@SuppressWarnings("all")
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private SolrClient solrClient;

    @Override
    public ResponseCode search(Map<String, Object> searchMap) {
        Integer current = (Integer) searchMap.get("current"); //获取当前页码
        Integer rows = (Integer) searchMap.get("rows"); //获取查询多少行记录
        String keyword = ((String) searchMap.get("keyword")).replace(" ", ""); //获取查询关键字
        return solrTemplateSearch(keyword, current, rows);
//        return solrQuerySearch(keyword, current, rows);
    }

    private ResponseCode solrTemplateSearch(String keyword, Integer current, Integer rows) {
        //高亮配置
        HighlightQuery query = new SimpleHighlightQuery();
        String[] fieldNames = {"username", "email", "qq"};
        HighlightOptions highlightOptions = new HighlightOptions().addField(fieldNames); //设置高亮域
        highlightOptions.setSimplePrefix("<em style='color: red'>"); //设置高亮前缀
        highlightOptions.setSimplePostfix("</em>"); //设置高亮后缀
        query.setHighlightOptions(highlightOptions); //设置高亮选项

        if ("".equals(keyword)) {
            return new ResponseCode("请输入查询内容");
        }

        try {
            /**
             * 通过Criteria构建查询过滤条件
             * 其中这里的`keyword`等价于solr core中schema.xml配置的域，且`keyword`是复制域名
             * 因为查询的内容是不确定的，solr提供了复制域实现同时查询多个域中的数据，并返回匹配的结果
             */
            Criteria criteria = new Criteria("keyword");
            //按照关键字查询
            if (keyword != null && !"".equals(keyword)) {
                criteria.contains(keyword);
            }
            query.addCriteria(criteria);

//            //构建查询条件
//            FilterQuery filterQuery = new SimpleFilterQuery();
//            Criteria filterCriteria = new Criteria("field");
//            filterCriteria.contains(keywords);
//            filterQuery.addCriteria(filterCriteria);
//            query.addFilterQuery(filterQuery);

            if (current == null) {
                current = 1; //默认第一页
            }
            if (rows == null) {
                rows = 20; //默认每次查询20条记录
            }
            query.setOffset((long) ((current - 1) * rows)); //从第几条记录开始查询：= 当前页 * 每页的记录数
            query.setRows(rows);

            HighlightPage<Search> page = solrTemplate.queryForHighlightPage("", query, Search.class);
            //循环高亮入口集合
            for (HighlightEntry<Search> h : page.getHighlighted()) {
                Search search = h.getEntity(); //获取原实体类
                if (h.getHighlights().size() > 0) {
                    h.getHighlights().forEach(light -> {
                        if (search.getUsername().contains(keyword)) {
                            search.setUsername(light.getSnipplets().get(0));
                        }
                        if (search.getEmail().contains(keyword)) {
                            search.setEmail(light.getSnipplets().get(0));
                        }
                        if (search.getQq().contains(keyword)) {
                            search.setQq(light.getSnipplets().get(0));
                        }
                    });
                }
            }
            return new ResponseCode(page.getContent(), page.getTotalElements());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseCode("查询失败");
        }
    }

    private ResponseCode solrQuerySearch(String keyword, Integer current, Integer rows) {
        if (StringUtils.isEmpty(keyword)) {
            return new ResponseCode("请输入查询内容");
        }
        if (current == 1) {
            current = 0;
        } else {
            current = (current - 1) * rows;
        }
        SolrQuery query = new SolrQuery();
        query.setStart(current);
        query.setRows(rows);
        query.set("wt", "json");
        try {
            String[] fields = {"username", "email", "qq", "password"}; //设置solr中定义的域

            //高亮配置
            String[] lightNames = {"username", "email", "qq"}; //设置需要高亮的域
            query.setParam("hl", "true");
            query.setParam("hl.fl", lightNames);
            query.setHighlightSimplePre("<em  style='color: red'>");
            query.setHighlightSimplePost("</em>");

            /**
             * 设置查询关键字的域
             * 一般对应solr中的复制域(<copyFiled>)。
             * 因为用户查询的数据不确定是什么，定义在复制域中的字段，Solr会自动进行多字段查询匹配
             */
            query.set("q", "keyword:*" + keyword + "*"); //在Solr中查询语句：/select?q=keyword:xxx

            QueryResponse response = solrClient.query(query);
            //获取被高亮的数据集合，其中的数据结构类似：[{id: "123", field: "<em>xxx</em>"}]
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            SolrDocumentList documents = response.getResults(); //获取匹配结果
            long numFound = documents.getNumFound(); //获取匹配的数据个数
            if (numFound != 0) {
                List<Object> entityList = new ArrayList<>();
                for (SolrDocument document : documents) {
                    //documents中存放了匹配的所有数据（未高亮），而highlighting中存放了高亮匹配的数据（高亮）
                    //通过id主键获取到id值，在highlighting中通过id值获取对应的高亮数据
                    Map<String, List<String>> listMap = highlighting.get(document.getFieldValue("id").toString());
                    for (int i = 0; i < lightNames.length; i++) {
                        if (listMap.get(lightNames[i]) != null) {
                            //根据设置的高亮域，将documents中未高亮的域的值替换为高亮的值
                            document.setField(lightNames[i], listMap.get(lightNames[i]).get(0));
                        }
                    }
                    Map<String, Object> fieldMap = new HashMap<>();
                    for (int i = 0; i < fields.length; i++) {
                        fieldMap.put(fields[i], String.valueOf(document.getFieldValue(fields[i])));
                    }
                    entityList.add(fieldMap);
                }
                return new ResponseCode(entityList, numFound);
            } else {
                return new ResponseCode("未搜索到任何结果");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseCode("服务器异常");
        }
    }
}
