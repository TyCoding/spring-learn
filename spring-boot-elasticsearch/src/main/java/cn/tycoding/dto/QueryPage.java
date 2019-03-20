package cn.tycoding.dto;

import lombok.Data;

/**
 * @author tycoding
 * @date 2019-03-20
 */
@Data
public class QueryPage {

    /**
     * 当前页
     */
    private Integer current;

    /**
     * 每页记录数
     */
    private Integer size;
}
