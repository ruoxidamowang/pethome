package com.ruoxi.gm.service;

import com.ruoxi.PetHomeApplication;
import com.ruoxi.gm.query.ProductQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author 若兮
 * @create 2022-03-07 11:16
 */
@SpringBootTest(classes = PetHomeApplication.class)
@RunWith(SpringRunner.class)
public class ProductServiceTest {
    @Resource
    private IProductService service;

    @Test
    public void findByPage() {
        System.out.println(service.findByPage(new ProductQuery()));
    }
}
