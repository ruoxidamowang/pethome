package com.ruoxi.gm.service;

import com.ruoxi.PetHomeApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author 若兮
 * @create 2022-03-02 9:06
 */
@SpringBootTest(classes = PetHomeApplication.class)
@RunWith(SpringRunner.class)
public class EmailTest {
    @Resource
    private JavaMailSender javaMailSender;

    @Test
    public void send(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //设置发送人
        mailMessage.setFrom("2435169750@qq.com");
        //邮件主题
        mailMessage.setSubject("新型冠状病毒防护指南");
        //邮件内容
        mailMessage.setText("好好在家待着.....");
        //收件人
        mailMessage.setTo("2435169750@qq.com");
        javaMailSender.send(mailMessage);
    }

    @Test
    public void sendEmail() throws MessagingException {
        //创建复杂邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //发送复杂邮件的工具类
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"utf-8");
        helper.setFrom("2435169750@qq.com");
        helper.setSubject("若兮");
        //"D:\test\bizhi.jpg"
        //http://img30.360buyimg.com/popWaterMark/jfs/t1/67988/7/14094/232759/5db64acfE6ab2b09e/38b5cb3dc38b4b1f.jpg"
        helper.setText("<h1>I need young woman.</h1>",true);
        //添加附件
        helper.addAttachment("壁纸.jpg",new File("D:\\test\\bizhi.jpg"));

        //收件人
        helper.setTo("1530838574@qq.com");

        javaMailSender.send(mimeMessage);
    }
}
