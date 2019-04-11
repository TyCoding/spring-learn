package cn.tycoding.config;

import cn.tycoding.constants.SecurityConstants;
import cn.tycoding.filter.ValidateCodeFilter;
import cn.tycoding.handler.AuthenticationSuccessHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author tycoding
 * @date 2019-04-09
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandlerImpl authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        /**
         * addFilterBefore(): 自定义添加一个过滤器，并且放在Spring Security某个过滤器的前面
         * fromLogin(): 表单认证
         * httpBasic(): 弹出框认证
         * loginPage(): 登录页面地址（因为SpringBoot无法直接访问页面，所以这通常是一个路由地址）
         * loginProcessingUrl(): 登录表单提交地址
         * successHandler(): 自定义身份校验成功成功处理器
         * failureHandler(): 自定义身份校验失败失败处理器
         * authorizeRequests() 身份认证请求
         * anyRequest(): 所有请求
         * authenticated(): 身份认证
         * .csrf().disable(): 关闭Spring Security的跨站请求伪造的功能
         */
        http
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/auth/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)

                //记住我
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(SecurityConstants.REMEMBERMESECONDS)
                .userDetailsService(userDetailsService)

                .and()
                .authorizeRequests()
                .antMatchers("/login", "/code/image").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
}
