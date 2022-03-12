package com.ruoxi.gm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoxi.gm.domain.Employee;
import com.ruoxi.gm.mapper.EmployeeMapper;
import com.ruoxi.gm.query.EmployeeQuery;
import com.ruoxi.gm.service.IEmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 若兮
 * @create 2022-02-24 14:45
 */
@Service
@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
public class EmployeeServiceImpl implements IEmployeeService {
    @Resource
    private EmployeeMapper mapper;

    @Override
    @Transactional
    public void add(Employee e) {

    }

    @Override
    @Transactional
    public void del(Long id) {
        mapper.remove(id);
    }

    @Override
    public PageInfo<EmployeeQuery> findByPage(EmployeeQuery query) {
        PageHelper.startPage(query.getStart(), query.getPageSize());
        List<EmployeeQuery> employees = mapper.loadAll(query);
        Page<EmployeeQuery> page= (Page<EmployeeQuery>) employees;
        return page.toPageInfo();
    }

    @Override
    @Transactional
    public void removeAll(ArrayList<Long> ids) {
        mapper.dels(ids);
    }

    @Override
    public List<Employee> findAllEmp() {
        return mapper.loadAllEmp();
    }

    @Override
    @Transactional
    public void changeRole(EmployeeQuery emp) {
        mapper.removeRole(emp.getId());
        mapper.addRole(emp);
    }
}
