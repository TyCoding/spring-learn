package cn.tycoding.service;

import cn.tycoding.config.AuthUser;
import cn.tycoding.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author tycoding
 * @date 2019-04-23
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * 实现UserDetailsService中的loadUserByUsername方法，用于加载用户数据
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //用户权限列表。这里是为了方便模拟，实际应该从权限表中查询用户的权限列表
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.getAuthorities());

        return new AuthUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }
}
