package cn.tycoding;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tycoding
 * @date 2019-02-27
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendMail() {
        mailService.sendMail("tycoding_tumo@163.com", "测试邮件", "hello this test email");
    }

    @Test
    public void sendHtmlMail() {
        String html = "<html>\n" +
                "<body>\n" +
                "<h2>this a html email</h2>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlMail("tycoding_tumo@163.com", "测试HTML邮件", html);
    }

    @Test
    public void sendAttachmentsMail() {
        String filePath = "/tycoding/learn-plan.md";
        mailService.sendAttachmentsMail("tycoding_tumo@163.com", "测试携带附件的邮件", "这是携带附件的邮件，请注意查收", filePath);
    }

    @Test
    public void sendInlineResourceMail() {
        String to = "tycoding_tumo@163.com";
        String subject = "这是携带内联资源的邮件";
        String resId = "001";
        String resPath = "/tycoding/totoro.png";
        String content = "<html>\n" +
                "<body>\n" +
                "这是带图片的邮件<img src=\'resId:" + resId + "\'>" +
                "</body>\n" +
                "</html>";
        mailService.sendInlineResourceMail(to, subject, content, resPath, resId);
    }

    @Test
    public void sendTemplateMail() {
        int code = (int) (Math.random() * 9 + 1) * 100000;
        mailService.sendTemplateMail("tycoding_tumo@163.com", "这是模板邮件", String.valueOf(code));
    }
}