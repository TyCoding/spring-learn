package cn.tycoding.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义异常处理类
 *
 * @author tycoding
 * @date 2019-04-10
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
