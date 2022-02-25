package com.ruoxi.gm.service.impl;

import com.ruoxi.gm.domain.Employee;
import com.ruoxi.gm.mapper.EmployeeMapper;
import com.ruoxi.gm.service.IEmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 若兮
 * @create 2022-02-24 14:45
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {
    @Resource
    private EmployeeMapper mapper;

    @Override
    public List<Employee> findAllEmp() {
        return mapper.loadAllEmp();
    }
}
