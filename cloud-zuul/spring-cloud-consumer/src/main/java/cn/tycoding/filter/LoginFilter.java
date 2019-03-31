package cn.tycoding.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录校验
 *
 * @author tycoding
 * @date 2019-03-31
 */
@Component
public class LoginFilter extends ZuulFilter {

    @Override
    public String filterType() {
        //路由前调用
        return "pre";
    }

    @Override
    public int filterOrder() {
        //数字越大，优先级越低
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //是否执行该过滤器
        return true;
    }

    /**
     * 核心逻辑
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("========zuul filter========");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String token = request.getParameter("x-token");
        if (token == null || "".equals(token.trim())) {
            //没有token，拦截
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return null;
    }
}
