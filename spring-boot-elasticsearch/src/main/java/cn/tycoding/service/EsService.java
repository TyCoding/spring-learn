package cn.tycoding.service;

import cn.tycoding.dto.QueryPage;
import cn.tycoding.entity.Article;
import org.springframework.data.domain.Page;

/**
 * @author tycoding
 * @date 2019-03-20
 */
public interface EsService {

    /**
     * 添加
     *
     * @param article
     */
    void add(Article article);

    /**
     * 模糊查询
     *
     * @param keyword
     * @param queryPage
     * @return
     */
    Page<Article> search(String keyword, QueryPage queryPage);
}
