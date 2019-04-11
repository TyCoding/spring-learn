package cn.tycoding.constants;

/**
 * 存放系统的一些常量数据
 *
 * @author tycoding
 * @date 2019-04-10
 */
public interface SecurityConstants {

    /**
     * 系统登录表单提交接口
     */
    String LOGIN_URL = "/auth/login";

    /**
     * POST,GET请求
     */
    String POST = "post";
    String GET = "get";

    /**
     * RememberMe过期时间
     */
    int REMEMBERMESECONDS = 60;
}
