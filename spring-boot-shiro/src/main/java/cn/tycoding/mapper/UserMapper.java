package cn.tycoding.mapper;

import cn.tycoding.entity.User;
import org.apache.ibatis.annotations.Select;

/**
 * @author tycoding
 * @date 2019-02-24
 */
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    User findByUsername(String username);
}
