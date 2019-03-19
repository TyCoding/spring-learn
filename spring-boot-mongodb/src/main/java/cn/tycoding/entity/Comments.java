package cn.tycoding.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tycoding
 * @date 2019-03-19
 */
@Data
public class Comments implements Serializable {

    @Id
    private Long _id;
    private Date createTime;
    private String username;
    private String content;
    private Long visits;
}
