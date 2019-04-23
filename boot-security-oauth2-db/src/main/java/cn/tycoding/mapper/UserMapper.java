package cn.tycoding.mapper;

import cn.tycoding.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author tycoding
 * @date 2019-04-23
 */
@Mapper
public interface UserMapper {

    @Select("select * from sys_user where username = #{username}")
    SysUser findByUsername(String username);
}
