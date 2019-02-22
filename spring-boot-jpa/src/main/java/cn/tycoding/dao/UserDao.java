package cn.tycoding.dao;

import cn.tycoding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tycoding
 * @date 2019-02-18
 */
public interface UserDao extends JpaRepository<User, Long> {

    @Query("select u from User u where u.password = ?1")
    @Transactional(timeout = 10)
    User findByPassword(String s);

    @Query("delete from User where password = ?1")
    @Modifying
    @Transactional
    void deleteByPassword(String s);
}
