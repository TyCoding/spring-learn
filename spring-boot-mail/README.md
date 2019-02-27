# Spring Boot整合邮件服务

Spring Boot也对`JavaMail`进行了封装简化。以前学JavaWeb的时候学习过调用JavaMail进行邮件，那时候需要配置的选项还是蛮多的，今天看到 [纯洁的微笑](http://www.ityouknow.com/springboot/2017/05/06/springboot-mail.html) 大佬关于SpringBoot邮件服务的文章，如今也可以愉快的玩一下邮件发送。

# 准备

## 导入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

## 修改`application.yml`

```yaml
spring:
  mail:
    #邮件服务器地址
    host: smtp.163.com
    #用户名
    username: tycoding_tumo@163.com
    #密码
    password: 客户端授权密码
    default-encoding: UTF-8
    properties:
      mail:
        smtp.auth: true

  thymeleaf:
    mode: LEGACYHTML5
    cache: false

#发件人邮箱
mail.from: tycoding_tumo@163.com
```

**注意**

> spring.mail.host是邮件服务器的地址

可以登录自己的163邮箱查看（其他邮箱雷同）：

```
服务器地址:
POP3服务器: pop.163.com
SMTP服务器: smtp.163.com
IMAP服务器: imap.163.com
```

> username是邮箱登录名

这里我全以163邮箱为例

> password是客户端授权密码不是登录密码

因为需要使用smtp服务，需要在163邮箱设置中开启smtp服务（其他邮箱也是），而在163邮箱中开启smtp服务需要设置客户端授权码，这个密码就是我们第三方API请求163服务的验证密码

> mail.from是发件人邮箱

名字是自定义的。一般情况下，`spring.mail.username`和`mail.from`是一样的

# 邮件服务

## 基础邮件

创建一个服务层方法：

```java
@Component
public class MailService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.from}")
    private String from;

    /**
     * 发送普通邮件
     *
     * @param to      收件人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendMail(String to, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);

        try {
            mailSender.send(mailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

测试方法：

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendMail() {
        mailService.sendMail("tycoding_tumo@163.com", "测试邮件", "hello this test email");
    }
}
```

## HTML格式邮件

Service服务层方法：

```java
    /**
     * 发送HTML格式的邮件
     *
     * @param to      收件人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
```

测试方法：

```java
    @Test
    public void sendHtmlMail() {
        String html = "<html>\n" +
                "<body>\n" +
                "<h2>this a html email</h2>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlMail("tycoding_tumo@163.com", "测试HTML邮件", html);
    }
```

## 携带附件的邮件

Service服务层方法：

```java
    /**
     * 发送携带附件的邮件
     *
     * @param to       收件人邮箱
     * @param subject  邮件主题
     * @param content  邮件内容
     * @param filePath 文件地址
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
```

测试方法：

```java
    @Test
    public void sendAttachmentsMail() {
        String filePath = "/tycoding/learn-plan.md";
        mailService.sendAttachmentsMail("tycoding_tumo@163.com", "测试携带附件的邮件", "这是携带附件的邮件，请注意查收", filePath);
    }
```

## HTML内联资源邮件

Service服务层方法

```java
    /**
     * 发送HTML标签内携带资源的邮件
     *
     * @param to      收件人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param resPath 资源路径
     * @param resId   资源ID
     */
    public void sendInlineResourceMail(String to, String subject, String content, String resPath, String resId) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource resource = new FileSystemResource(new File(resPath));
            helper.addInline(resId, resource);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

测试方法：

```java
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
```

## 模板邮件

比如一些网站常用发送发送验证码到邮箱，通常这是用一个模板的，比如这样：

在`resources/`下创建模板`emailTemplate.html`

```html
<!DOCTYPE html>
<html lang="zh" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>邮件模板</title>
</head>
<body>

您好，欢迎注册本系统，您的验证码是： <b th:text="${code}"></b>

-- TyCoding

</body>
</html>
```

Service层方法：

```java
    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 发送模板邮件
     *
     * @param to      收件人邮箱
     * @param subject 邮件主题
     * @param code    邮件验证码
     */
    public void sendTemplateMail(String to, String subject, String code) {
        Context context = new Context();
        context.setVariable("code", code);
        String emailContent = templateEngine.process("emailTemplate", context);
        sendHtmlMail(to, subject, emailContent);
    }
```

测试方法：

```java
    @Test
    public void sendTemplateMail() {
        int code = (int) (Math.random() * 9 + 1) * 100000;
        mailService.sendTemplateMail("tycoding_tumo@163.com", "这是模板邮件", String.valueOf(code));
    }
```

# 常见错误

在实际测试中遇到了很多错误，比如：

## 550 User has no permission

> 解释

一般是因为你的163邮箱没有开启`客户端授权`，导致第三方API调用163服务被拒绝

> 解决办法

登录163邮箱，配置`客户端授权`，并且开启`设置POP3/SMTP/IMAP`服务

## 535 Error: authentication failed

> 解释

显然是说身份验证失败，一般是因为在`application.yml`中配置的`spring.mail.password`是你的邮箱登录密码

> 解决办法

这个`spring.mail.password`不能填写你的邮箱密码，要写**客户端授权密码**，具体要登录163邮箱查看并设置

## 553 Mail from must equal authorized user

> 解释

一般是因为`application.yml`中配置的`spring.mail.password`和`mail.from`(发件人邮箱)不一致导致的

> 解决办法

对比`application.yml`中配置的`spring.mail.password`和`mail.from`

## DT:SPM 163 smtp11,D8CowADX

```java
com.sun.mail.smtp.SMTPSendFailedException: 554 DT:SPM 163 smtp14,EsCowAB3K92VD3ZcPZPeHQ--.26803S2 1551241110
```

> 解释

出现这一串代码，一般是说明，你的配置中 发件人邮箱是163邮箱，收件人邮箱是其他邮箱（比如QQ邮箱）。

> 解决办法

经过查百度，163官方是说其不允许发送垃圾邮件，但是这种错误是时不时出现的。在我的测试中，之前几封邮件是从163发送给QQ邮箱是可以的，但是莫名后几次就开始报这个错误了。暂时也不清楚如何解决。只要是163邮箱发送给163邮箱（比如自己发送给自己）就不会报这个错。

