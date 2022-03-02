package com.ruoxi.gm;

import com.ruoxi.PetHomeApplication;
import com.ruoxi.gm.domain.Department;
import com.ruoxi.gm.query.DepartmentQuery;
import com.ruoxi.gm.service.IDepartmentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author 若兮
 * @create 2022-02-21 14:33
 */
@SpringBootTest(classes = PetHomeApplication.class)
@RunWith(SpringRunner.class)
class DepartmentServiceTest {
    @Resource
    private IDepartmentService service;

    @Test
    void add() {
        Department department = new Department();
        department.setName("人事部");
        service.add(department);
    }

    @Test
    void del() {
        service.del(48L);
    }

    @Test
    void update() {
//        Department department = service.findByPage(null);
//        department.setSn("DeptSSS");
//        service.update(department);
    }

    @Test
    void findByPage() {
        System.out.println(service.findByPage(new DepartmentQuery()));
    }
}
