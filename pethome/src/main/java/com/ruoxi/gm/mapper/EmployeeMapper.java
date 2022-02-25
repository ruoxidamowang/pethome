package com.ruoxi.gm.mapper;

import com.ruoxi.gm.domain.Employee;

import java.util.List;

/**
 * @author 若兮
 * @create 2022-02-24 14:32
 */
public interface EmployeeMapper {
    List<Employee> loadAllEmp();
}
