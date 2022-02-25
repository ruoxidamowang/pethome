package com.ruoxi.gm.service;

import com.ruoxi.PetHomeApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author 若兮
 * @create 2022-02-24 14:47
 */
@SpringBootTest(classes = PetHomeApplication.class)
@RunWith(SpringRunner.class)
public class IEmployeeServiceTest {
    @Resource
    private IEmployeeService service;

    @Test
    public void findAllEmp() {
        service.findAllEmp();
    }
}
