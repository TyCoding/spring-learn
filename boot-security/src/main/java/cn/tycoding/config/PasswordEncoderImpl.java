package cn.tycoding.config;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author tycoding
 * @date 2019-04-09
 */
//@Component
public class PasswordEncoderImpl implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}
