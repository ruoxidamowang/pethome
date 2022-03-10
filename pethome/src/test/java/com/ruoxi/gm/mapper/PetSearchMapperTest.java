package com.ruoxi.gm.mapper;

import com.ruoxi.PetHomeApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author 若兮
 * @create 2022-03-09 16:16
 */
@SpringBootTest(classes = PetHomeApplication.class)
@RunWith(SpringRunner.class)
public class PetSearchMapperTest {
    @Resource
    private PetSearchMapper mapper;

    @Test
    public void save() {
    }

    @Test
    public void remove() {
    }

    @Test
    public void update() {
    }

    @Test
    public void loadAll() {
    }

    @Test
    public void dels() {
    }

    @Test
    public void selectAll() {
        System.out.println(mapper.selectAll());
    }
}
