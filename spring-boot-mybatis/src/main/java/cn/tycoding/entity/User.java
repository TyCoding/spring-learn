package cn.tycoding.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tycoding
 * @date 2019-02-22
 */
@Data
@ToString
public class User implements Serializable {

    private Long id;
    private String username;
    private String password;
    private Date createTime;
}
