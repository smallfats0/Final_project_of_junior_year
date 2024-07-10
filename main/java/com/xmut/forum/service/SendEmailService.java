package com.xmut.forum.service;

import com.xmut.forum.constants.Constants;
import com.xmut.forum.util.RedisUtil;
import com.xmut.forum.verify.SimpleCharVerifyCodeGenImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

@Service
public class SendEmailService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String serverEmail;

    @Value("${xmutmail.subject}")
    private String subject;

    @Value("${xmutmail.personal}")
    private String personal;

    @Value("${xmutmail.code.timeout}")
    private int timeout;

    @Value(("${xmutmail.captchalen}"))
    private int captchaLen;

    /**
     * 发送验证码到邮箱
     * @throws Exception
     */
    public boolean sendVerify(String name, String email, HttpServletRequest request) throws Exception{

        //生成随机验证码
        SimpleCharVerifyCodeGenImpl verifyCodeGen = new SimpleCharVerifyCodeGenImpl();
        String code = verifyCodeGen.codestring(captchaLen);

        //将超时时间和邮件验证码存入redis中
        boolean isSet = redisUtil.set( Constants.CAPTCHA_CODE_KEY + request.getRemoteAddr() + ":" + name.trim(), code, timeout * 60);

        System.out.println(isSet);
        if (isSet) {

            StringBuilder stringBuilder = new StringBuilder();
            /**
             * 邮件发送模板
             */
            stringBuilder.append("<div style=\"width:500px; margin: auto\">\n" +
                    "    <div style=\"    background: #2695f3;\n" +
                    "    color: #FFF;\n" +
                    "    padding: 20px 10px;\">\n" +
                    "        欢迎注册 XMUT论坛！\n" + "    </div>\n" + "    <div style=\"\n" + "    padding: 10px;\n" + "    margin: 5px;\n" + "    border-bottom: dashed 1px #ddd;\n" + "    \">\n" + "        您的用户名是：<b>").append(name.trim()).append("</b><br>\n").append("\n").append("        您的验证码是 <b>").append(code).append("</b><br>\n").append("\n").append("        此链接当月有效。\n").append("\n").append("    </div>\n").append("    <br>\n").append("    <br>\n").append("    <div style=\"color:#cecece;font-size: 12px;\">\n").append("        本邮件为系统自动发送，请勿回复。\n").append("        <br>\n").append("    </div>\n").append("</div>");
//            stringBuilder.append("<html><head><title></title></head><body>");
//            stringBuilder.append("您好-感谢使用五七贴吧<br/>");
//            stringBuilder.append("您的验证码是：<strong style='color:red'>").append(code).append("</strong><br/>");
//            stringBuilder.append("此验证码用于验证您的邮箱账号。<br/>");
//            stringBuilder.append("此验证码<strong>5分钟内</strong>有效。<br/>");
//            stringBuilder.append("如果您没有进行上述操作，请忽略此邮件。");

            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setSubject(subject);

            helper.setText(stringBuilder.toString(), true);

            //发送者邮箱
            helper.setFrom(serverEmail);
            InternetAddress internetAddress = new InternetAddress(serverEmail,personal,Constants.UTF8);
            helper.setFrom(internetAddress);
            //接收者邮箱
            helper.setTo(email);

            mailSender.send(mimeMessage);
            return true;
        }
        return false;
    }

}
