package cn.tycoding.repository;

import cn.tycoding.entity.Demo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tycoding
 * @date 2019-02-22
 */
public interface DemoDao extends JpaRepository<Demo, Long> {
}
