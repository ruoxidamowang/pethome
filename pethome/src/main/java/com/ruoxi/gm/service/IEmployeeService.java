package com.ruoxi.gm.service;

import com.github.pagehelper.PageInfo;
import com.ruoxi.gm.domain.Employee;
import com.ruoxi.gm.query.EmployeeQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 若兮
 * @create 2022-02-24 14:45
 */
public interface IEmployeeService {
    void add(Employee e);
    void del(Long id);
    PageInfo<EmployeeQuery> findByPage(EmployeeQuery query);

    void removeAll(ArrayList<Long> ids);
    List<Employee> findAllEmp();

    void changeRole(EmployeeQuery emp);
}
