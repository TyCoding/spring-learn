package cn.tycoding.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tycoding
 * @date 2019-04-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable {

    private Long id;

    private String username;

    private String password;

    private String salt;
}
