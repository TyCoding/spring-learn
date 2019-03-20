package cn.tycoding.service.impl;

import cn.tycoding.entity.Comments;
import cn.tycoding.service.MongoService;
import cn.tycoding.dao.MongoDao;
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
    private MongoDao mongoDao;

    @Override
    public List<Comments> findAll() {
        return mongoDao.findAll();
    }

    @Override
    public Comments findById(Long id) {
        return mongoDao.findById(id.toString()).get();
    }

    @Override
    public void add(Comments comments) {
        comments.set_id(new Random().nextLong());
        mongoDao.save(comments);
    }

    @Override
    public void update(Comments comments) {
        mongoDao.save(comments);
    }

    @Override
    public void delete(List<Long> ids) {
        ids.forEach(id -> {
            mongoDao.deleteById(id.toString());
        });
    }
}
