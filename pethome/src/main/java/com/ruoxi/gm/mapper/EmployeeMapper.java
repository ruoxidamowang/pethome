package com.ruoxi.gm.mapper;

import com.ruoxi.gm.domain.Employee;
import com.ruoxi.gm.query.EmployeeQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 若兮
 * @create 2022-02-24 14:32
 */
public interface EmployeeMapper {
    void save(Employee e);
    void remove(Long id);
    void removeRole(Long id);
    List<EmployeeQuery> loadAll(EmployeeQuery query);

    void dels(ArrayList<Long> ids);

    List<Employee> loadAllEmp();

    void addRole(EmployeeQuery emp);
}
