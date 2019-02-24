package cn.tycoding.service;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-24
 */
public interface UserRoleService {

    void deleteUserRolesByUserId(List<Long> keys);
}
