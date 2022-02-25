package com.ruoxi.gm.service;

import com.github.pagehelper.PageInfo;
import com.ruoxi.gm.domain.Department;
import com.ruoxi.gm.query.DepartmentQuery;

import java.util.ArrayList;

/**
 * @author 若兮
 * @create 2022-02-21 14:06
 */
public interface IDepartmentService {
    void add(Department d);
    void del(Long id);
    void update(Department d);
    PageInfo<Department> findByPage(DepartmentQuery query);

    void removeAll(ArrayList<Long> ids);
}
