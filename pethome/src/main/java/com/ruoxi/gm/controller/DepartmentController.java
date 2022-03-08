package com.ruoxi.gm.controller;

import com.ruoxi.basic.common.Result;
import com.ruoxi.gm.domain.Department;
import com.ruoxi.gm.query.DepartmentQuery;
import com.ruoxi.gm.service.IDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author 若兮
 * @create 2022-02-21 14:43
 */
@RestController
@RequestMapping("/department")
@Api(tags = "部门接口")
public class DepartmentController {
    @Resource
    private IDepartmentService service;

    @ApiOperation(value = "部门添加或修改",notes = "有id则为修改，没有id则为添加")
    @PutMapping
    private Result addOrUpdate(@RequestBody Department department) {
        if (department.getId() != null) {
            try {
                service.update(department);
                return Result.me().setMsg("修改成功");
            } catch (Exception e) {
                return Result.me(false).setMsg("修改失败-" + e.getCause());
            }
        } else {
            try {
                service.add(department);
                return Result.me().setMsg("添加成功");
            } catch (Exception e) {
                return Result.me(false).setMsg("添加失败-" + e.getCause());
            }
        }
    }

    @ApiOperation("根据id删除部门")
    @DeleteMapping("/{id}")
    private Result delete(@PathVariable("id") Long id) {
        try {
            service.del(id);
            return Result.me().setMsg("删除成功");
        } catch (Exception e) {
            return Result.me(false).setMsg("删除失败-" + e.getCause());
        }
    }

    @ApiOperation("根据id批量删除部门")
    @DeleteMapping
    private Result deletes(@RequestBody ArrayList<Long> ids) {
        try {
            service.removeAll(ids);
            return Result.me().setMsg("批量删除成功");
        } catch (Exception e) {
            return Result.me(false).setMsg("删除失败-" + e.getCause());
        }
    }

    @ApiOperation("分页查询部门")
    @PostMapping
    private Result findByPage(@RequestBody DepartmentQuery query) {
        return Result.me(service.findByPage(query));
    }

    @ApiOperation("查询所有部门信息")
    @GetMapping
    private Result findAll() {
        return Result.me(service.findAllDept());
    }
}
