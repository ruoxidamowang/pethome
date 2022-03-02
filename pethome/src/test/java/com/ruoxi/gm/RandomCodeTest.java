package com.ruoxi.gm;

import cn.hutool.core.util.RandomUtil;
import com.ruoxi.PetHomeApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 若兮
 * @create 2022-03-02 14:33
 */
@SpringBootTest(classes = PetHomeApplication.class)
@RunWith(SpringRunner.class)
public class RandomCodeTest {
    @Test
    public void randomCode() {
        String str = RandomUtil.randomString(32);
        String s = RandomUtil.randomNumbers(4);
        System.out.println(str);
        System.out.println(s);
    }
}
