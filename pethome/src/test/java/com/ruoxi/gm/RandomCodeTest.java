package com.ruoxi.gm;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoxi.PetHomeApplication;
import com.ruoxi.basic.common.MD5Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author 若兮
 * @create 2022-03-02 14:33
 */
@SpringBootTest(classes = PetHomeApplication.class)
@RunWith(SpringRunner.class)
public class RandomCodeTest {
    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis(){
        System.out.println(redisTemplate.opsForValue().get("17671794587"));
    }

    @Test
    public void randomCode() {
        String str = RandomUtil.randomString(32);
        String s = RandomUtil.randomNumbers(4);
        byte[] c = RandomUtil.randomBytes(10);
        String ss = "";
        System.out.println(StrUtil.hasEmpty(ss));
        System.out.println(Arrays.toString(c));
        System.out.println(str);
        System.out.println(s);
    }

    @Test
    public void MD5Test(){
        //加密
        //1 生成随机盐值
        String pwd = "123456";
        String salt = RandomUtil.randomString(32);; //盐，，，随机串，安全
        //2 通过这个盐值加密
        String md5Pwd = MD5Utils.encrypByMd5(pwd + "ruo" + salt + "xi");
        System.out.println(md5Pwd);//密文
        //密码比对
        //1 查询盐值-就是salt
        String saltTmp = salt;
        //3 加密比对
        String pwdTmp = "123456";
        String inputMd5Pwd = MD5Utils.encrypByMd5(pwdTmp + "ruo" + saltTmp + "xi");
        if (inputMd5Pwd.equals(md5Pwd)) {
            System.out.println("登录成功!");
        } else {
            System.out.println("密码错误");
        }
    }
}
