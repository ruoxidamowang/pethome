package com.ruoxi.gm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoxi.gm.domain.Department;
import com.ruoxi.gm.mapper.DepartmentMapper;
import com.ruoxi.gm.query.DepartmentQuery;
import com.ruoxi.gm.service.IDepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 若兮
 * @create 2022-02-21 14:24
 */
@Service
@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
public class DepartmentServiceImpl implements IDepartmentService {
    @Resource
    private DepartmentMapper mapper;

    @Transactional
    @Override
    public void add(Department d) {
        mapper.save(d);
    }

    @Transactional
    @Override
    public void del(Long id) {
        mapper.remove(id);
    }

    @Transactional
    @Override
    public void update(Department d) {
        mapper.update(d);
    }

    @Override
    public PageInfo<Department> findByPage(DepartmentQuery query) {
        PageHelper.startPage(query.getStart(), query.getPageSize());
        List<Department> departments = mapper.loadAll(query);
        Page<Department> page= (Page<Department>) departments;
        return page.toPageInfo();
    }
}
