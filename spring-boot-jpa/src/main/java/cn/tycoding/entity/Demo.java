package cn.tycoding.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-18
 */
@Data
@Entity
@Table(name = "tb_demo")
public class Demo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "create_time")
    private Date createTime;

    @Transient
    private List list;
}
