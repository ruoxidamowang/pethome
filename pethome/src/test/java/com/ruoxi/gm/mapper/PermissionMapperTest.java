package com.ruoxi.gm.mapper;

import com.ruoxi.PetHomeApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author 若兮
 * @create 2022-03-14 14:48
 */
@SpringBootTest(classes = PetHomeApplication.class)
@RunWith(SpringRunner.class)
public class PermissionMapperTest {
    @Resource
    private PermissionMapper mapper;

    @Test
    public void selectAllPermission() {
        System.out.println(mapper.selectAllPermission());
    }
}
