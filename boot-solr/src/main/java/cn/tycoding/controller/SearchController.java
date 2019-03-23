package cn.tycoding.controller;

import cn.tycoding.service.SearchService;
import cn.tycoding.utils.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author tycoding
 * @date 2019-03-03
 */
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public ResponseCode search(@RequestBody Map<String, Object> searchMap) {
        return searchService.search(searchMap);
    }
}
