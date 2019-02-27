package cn.tycoding.controller;

import cn.tycoding.dto.ResponseCode;
import cn.tycoding.entity.Article;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 遵循Restful接口
 *
 * @author tycoding
 * @date 2019-02-27
 */
@RestController
@RequestMapping("/article")
@Api(value = "ArticleController", tags = {"文章管理接口"})
public class ArticleController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation(value = "查询文章详情", notes = "文章ID大于0")
    @ApiImplicitParam(name = "id", value = "文章编号", required = true, dataType = "Long")
    public ResponseCode findById(@PathVariable Long id) {
        logger.info("查询文章信息，查询的文章ID是==> {}", id);
        Article article = new Article(1L, "Swagger", "SpringBoot整合Swagger2", new Date());
        return ResponseCode.ok("查询成功", article);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ApiOperation(value = "删除文章")
    @ApiImplicitParam(name = "id", value = "文章编号", required = true, dataType = "Long")
    public ResponseCode delete(@PathVariable Long id) {
        logger.info("删除文章信息，删除的文章ID是==> {}", id);
        return ResponseCode.ok("删除成功");
    }

    @PostMapping(value = "/", produces = "application/json")
    @ApiOperation(value = "保存文章")
    @ApiImplicitParam(name = "article", value = "文章信息实体", required = true, dataType = "Article", paramType = "body")
    public ResponseCode save(@RequestBody Article article) {
        logger.info("保存文章信息，文章内容==> {}", article);
        return ResponseCode.ok("保存成功");
    }

    @PutMapping(value = "/", produces = "application/json")
    @ApiOperation(value = "更新文章")
    @ApiImplicitParam(name = "article", value = "文章信息实体", required = true, dataType = "Article", paramType = "body")
    public ResponseCode update(@RequestBody Article article) {
        logger.info("更新文章信息，更新内容==> {}", article);
        return ResponseCode.ok("更新成功");
    }
}
