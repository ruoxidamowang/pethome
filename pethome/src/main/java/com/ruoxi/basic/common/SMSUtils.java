package com.ruoxi.basic.common;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

public class SMSUtils {

    public static String sendSMS(String phone, String content) {
        try {
            HttpClient client = new HttpClient();
            PostMethod post = new PostMethod("http://utf8.api.smschinese.cn");
            post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");//在头文件中设置转码
            NameValuePair[] data = {new NameValuePair("Uid", "樱岛麻衣"), new NameValuePair("Key", "d41d8cd98f00b204e980"), new NameValuePair("smsMob", phone), new NameValuePair("smsText", "验证码为："+content)};
            post.setRequestBody(data);

            client.executeMethod(post);
            Header[] headers = post.getResponseHeaders();
            int statusCode = post.getStatusCode();
            System.out.println("statusCode:" + statusCode);
            for (Header h : headers) {
                System.out.println(h.toString());
            }
            String result = new String(post.getResponseBodyAsString().getBytes("utf-8"));
            System.out.println(result); //打印返回消息状态

            post.releaseConnection();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
