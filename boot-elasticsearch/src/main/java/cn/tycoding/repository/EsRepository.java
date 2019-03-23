package cn.tycoding.repository;

import cn.tycoding.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author tycoding
 * @date 2019-03-20
 */
public interface EsRepository extends ElasticsearchRepository<Article, String> {

    /**
     * 根据文章标题或内容模糊查询
     *
     * @param title
     * @param content
     * @param pageable
     * @return
     */
    Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
