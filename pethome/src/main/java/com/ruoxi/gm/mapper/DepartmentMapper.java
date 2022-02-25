package com.ruoxi.gm.mapper;

import com.ruoxi.gm.domain.Department;
import com.ruoxi.gm.query.DepartmentQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 若兮
 * @create 2022-02-21 13:59
 */
public interface DepartmentMapper {
    void save(Department d);
    void remove(Long id);
    void update(Department d);
    List<Department> loadAll(DepartmentQuery query);

    void dels(ArrayList<Long> ids);

    List<Department> selectAll();
}
