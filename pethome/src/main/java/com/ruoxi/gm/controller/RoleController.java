package com.ruoxi.gm.controller;

import com.ruoxi.basic.common.Result;
import com.ruoxi.gm.domain.Role;
import com.ruoxi.gm.query.RoleQuery;
import com.ruoxi.gm.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 若兮
 * @create 2022-03-12 14:28
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色管理接口")
public class RoleController {
    @Resource
    private IRoleService service;

    @ApiOperation(value = "角色添加或修改",notes = "有id则为修改，没有id则为添加")
    @PutMapping
    private Result addOrUpdate(@RequestBody Role role) {
        if (role.getId() != null) {
            try {
                service.update(role);
                return Result.me().setMsg("修改成功");
            } catch (Exception e) {
                return Result.me(false).setMsg("修改失败-" + e.getCause());
            }
        } else {
            try {
                service.add(role);
                return Result.me().setMsg("添加成功");
            } catch (Exception e) {
                return Result.me(false).setMsg("添加失败-" + e.getCause());
            }
        }
    }

    @ApiOperation("根据id删除角色")
    @DeleteMapping("{id}")
    private Result delete(@PathVariable Long id) {
        try {
            service.del(id);
            return Result.me().setMsg("删除成功！");
        } catch (Exception e) {
            return Result.me(false).setMsg("删除失败-" + e.getCause());
        }
    }

    @ApiOperation("分页查询角色")
    @PostMapping
    private Result findByPage(@RequestBody RoleQuery query) {
        return Result.me(service.findByPage(query));
    }

    @ApiOperation("查询所有角色")
    @GetMapping
    private Result findAll() {
        return Result.me(service.findAllRole());
    }
}
