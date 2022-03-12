package com.ruoxi.gm.service;

import com.github.pagehelper.PageInfo;
import com.ruoxi.gm.domain.Role;
import com.ruoxi.gm.query.RoleQuery;

import java.util.List;

/**
 * @author 若兮
 * @create 2022-03-12 16:07
 */
public interface IRoleService {
    void add(Role r);
    void del(Long id);
    void update(Role r);
    PageInfo<Role> findByPage(RoleQuery query);
    List<Role> findAllRole();
}
