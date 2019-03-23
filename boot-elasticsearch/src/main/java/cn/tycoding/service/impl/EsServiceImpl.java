package cn.tycoding.service.impl;

import cn.tycoding.dto.QueryPage;
import cn.tycoding.entity.Article;
import cn.tycoding.repository.EsRepository;
import cn.tycoding.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author tycoding
 * @date 2019-03-20
 */
@Service
public class EsServiceImpl implements EsService {

    @Autowired
    private EsRepository esRepository;


    @Override
    public void add(Article article) {
        esRepository.save(article);
    }

    @Override
    public Page<Article> search(String keyword, QueryPage queryPage) {
        PageRequest pageRequest = PageRequest.of(queryPage.getCurrent() - 1, queryPage.getSize());
        return esRepository.findByTitleOrContentLike(keyword, keyword, pageRequest);
    }
}
