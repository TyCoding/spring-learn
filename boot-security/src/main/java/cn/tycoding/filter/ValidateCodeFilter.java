package cn.tycoding.filter;

import cn.tycoding.constants.ImageConstants;
import cn.tycoding.constants.SecurityConstants;
import cn.tycoding.exception.ValidateCodeException;
import cn.tycoding.utils.ImageCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义验证码过滤器
 *
 * @author tycoding
 * @date 2019-04-10
 */
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Getter
    @Setter
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Getter
    @Setter
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals(SecurityConstants.LOGIN_URL, request.getRequestURI()) && StringUtils.equalsIgnoreCase(request.getMethod(), SecurityConstants.POST)) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request, ImageConstants.SESSION_KEY_CODE);
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), ImageConstants.LOGIN_FORM_CODE);
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }
        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (codeInSession.isExpire()) {
            sessionStrategy.removeAttribute(request, ImageConstants.SESSION_KEY_CODE);
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(request, ImageConstants.SESSION_KEY_CODE);
    }
}
