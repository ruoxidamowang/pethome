package com.ruoxi.gm.service;

import com.ruoxi.gm.domain.Employee;

import java.util.List;

/**
 * @author 若兮
 * @create 2022-02-24 14:45
 */
public interface IEmployeeService {
    List<Employee> findAllEmp();
}
