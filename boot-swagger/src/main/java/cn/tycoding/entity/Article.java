package cn.tycoding.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tycoding
 * @date 2019-02-27
 */
@ApiModel(value = "Article", description = "文章实体对象")
public class Article implements Serializable {

    /**
     * @ApiModelProperty 用于描述实体字段
     *      value: 字段说明
     *      name: 重写字段名称
     *      dataType: 重写字段类型
     *      required: 字段是否必填
     *      example: 举例说明
     *      hidden: 是否隐藏显示
     */
    @ApiModelProperty(value = "id", example = "1", required = true)
    private Long id; //文章ID
    @ApiModelProperty(name = "name", value = "文章名称", example = "Swagger", required = true)
    private String name; //文章名称
    @ApiModelProperty(name = "title" ,value = "文章标题", example = "SpringBoot中使用Swagger", required = true)
    private String title; //文章标题
    @ApiModelProperty(name = "createTime", value = "创建时间", required = false)
    private Date createTime; //创建时间

    public Article() {
    }

    public Article(Long id, String name, String title, Date createTime) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
