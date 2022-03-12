package com.ruoxi.gm.mapper;

import com.ruoxi.gm.domain.Role;
import com.ruoxi.gm.query.RoleQuery;

import java.util.List;

/**
 * @author 若兮
 * @create 2022-03-12 16:05
 */
public interface RoleMapper {
    void save(Role r);
    void remove(Long id);
    void update(Role r);
    List<Role> loadAll(RoleQuery query);

    List<Role> selectAllRole();
}
