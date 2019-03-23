package cn.tycoding.service.impl;

import cn.tycoding.entity.Comments;
import cn.tycoding.repository.MongoDbRepository;
import cn.tycoding.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @author tycoding
 * @date 2019-03-19
 */
@Service
public class MongoServiceImpl implements MongoService {

    @Autowired
    private MongoDbRepository mongoDbRepository;

    @Override
    public List<Comments> findAll() {
        return mongoDbRepository.findAll();
    }

    @Override
    public Comments findById(Long id) {
        return mongoDbRepository.findById(id.toString()).get();
    }

    @Override
    public void add(Comments comments) {
        comments.set_id(new Random().nextLong());
        mongoDbRepository.save(comments);
    }

    @Override
    public void update(Comments comments) {
        mongoDbRepository.save(comments);
    }

    @Override
    public void delete(List<Long> ids) {
        ids.forEach(id -> {
            mongoDbRepository.deleteById(id.toString());
        });
    }
}
