package cn.tycoding.service;

import cn.tycoding.utils.ResponseCode;

import java.util.Map;

/**
 * @author tycoding
 * @date 2019-03-03
 */
public interface SearchService {

    /**
     * 搜索 -- 从Solr索引库中
     *
     * @param searchMap
     * @return
     */
    ResponseCode search(Map<String, Object> searchMap);
}
