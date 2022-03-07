package com.ruoxi.basic.common;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 若兮
 * @create 2022-03-04 10:40
 */
@Component
public class EmailUtils {
    @Resource
    private JavaMailSender javaMailSender;

    public void sendEmail(String to,String content){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //设置发送人
        mailMessage.setFrom("2435169750@qq.com");
        //邮件主题
        mailMessage.setSubject("斯塔克工业登录验证码");
        //邮件内容
        mailMessage.setText("【斯塔克工业】您的登录验证码为："+content);
        //收件人
        mailMessage.setTo(to);
        javaMailSender.send(mailMessage);
    }
}
