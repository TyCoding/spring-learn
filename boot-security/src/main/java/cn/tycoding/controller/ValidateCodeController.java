package cn.tycoding.controller;

import cn.tycoding.constants.ImageConstants;
import cn.tycoding.utils.ImageCode;
import cn.tycoding.utils.ImageCodeGenerator;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 获取验证码的控制层
 * @author tycoding
 * @date 2019-04-10
 */
@RestController
public class ValidateCodeController {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 获取图片验证码
     *
     * @param request
     * @param response
     */
    @GetMapping("/code/image")
    public void getImageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = ImageCodeGenerator.createImageCode();
        sessionStrategy.setAttribute(new ServletWebRequest(request), ImageConstants.SESSION_KEY_CODE, imageCode);
        ImageIO.write(imageCode.getImage(), ImageConstants.CODE_TYPE, response.getOutputStream());
    }
}
