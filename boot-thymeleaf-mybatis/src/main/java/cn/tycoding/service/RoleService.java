package cn.tycoding.service;

import cn.tycoding.entity.Role;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-24
 */
public interface RoleService extends BaseService<Role> {

    List<Role> queryList(Role role);
}
