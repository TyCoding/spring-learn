package cn.tycoding.service;

import cn.tycoding.entity.Comments;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-03-19
 */
public interface MongoService {

    /**
     * 查询所有
     *
     * @return
     */
    List<Comments> findAll();

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Comments findById(Long id);

    /**
     * 增加
     *
     * @param comments
     */
    void add(Comments comments);

    /**
     * 修改
     *
     * @param comments
     */
    void update(Comments comments);

    /**
     * 删除
     *
     * @param ids
     */
    void delete(List<Long> ids);
}
