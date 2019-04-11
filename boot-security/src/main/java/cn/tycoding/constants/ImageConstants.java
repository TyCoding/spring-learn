package cn.tycoding.constants;

/**
 * 封装图片验证码相关的常量
 *
 * @author tycoding
 * @date 2019-04-11
 */
public interface ImageConstants {

    /**
     * 验证码宽度
     */
    int CODE_WIDTH = 67;

    /**
     * 验证码高度
     */
    int CODE_HEIGHT = 23;

    /**
     * 验证码的过期时间
     */
    int CODE_EXPIREIN = 60;

    /**
     * 验证码的位数
     */
    int CODE_LENGTH = 4;

    /**
     * 验证码字体大小
     */
    int CODE_FONT_SIZE = 20;

    /**
     * 验证码格式
     */
    String CODE_TYPE = "JPEG";

    /**
     * Session中存验证码的key值
     */
    String SESSION_KEY_CODE = "SESSION_KEY_IMAGE_CODE";

    /**
     * 登录表单验证码name值
     */
    String LOGIN_FORM_CODE = "code";
}
