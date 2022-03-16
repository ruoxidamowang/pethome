package com.ruoxi.gm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoxi.gm.domain.Role;
import com.ruoxi.gm.mapper.RoleMapper;
import com.ruoxi.gm.query.RoleQuery;
import com.ruoxi.gm.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 若兮
 * @create 2022-03-12 16:09
 */
@Service
@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
public class RoleServiceImpl implements IRoleService {
    @Resource
    private RoleMapper mapper;

    @Override
    @Transactional
    public void add(Role r) {
        mapper.save(r);
    }

    @Override
    @Transactional
    public void del(Long id) {
        mapper.removeEmpRole(id);
        mapper.remove(id);
    }

    @Override
    @Transactional
    public void update(Role r) {
        mapper.update(r);
    }

    @Override
    public PageInfo<Role> findByPage(RoleQuery query) {
        PageHelper.startPage(query.getStart(), query.getPageSize());
        List<Role> roles = mapper.loadAll(query);
        Page<Role> page= (Page<Role>) roles;
        return page.toPageInfo();
    }

    @Override
    public List<Role> findAllRole() {
        return mapper.selectAllRole();
    }
}
