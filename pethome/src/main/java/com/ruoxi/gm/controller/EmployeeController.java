package com.ruoxi.gm.controller;

import com.ruoxi.basic.common.Result;
import com.ruoxi.gm.query.EmployeeQuery;
import com.ruoxi.gm.service.IEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author 若兮
 * @create 2022-02-24 15:02
 */
@RestController
@RequestMapping("employee")
@Api(tags = "员工接口")
public class EmployeeController {
    @Resource
    private IEmployeeService service;

    @ApiOperation("根据id删除员工")
    @DeleteMapping("/{id}")
    private Result delete(@PathVariable("id") Long id) {
        try {
            service.del(id);
            return Result.me().setMsg("删除成功");
        } catch (Exception e) {
            return Result.me(false).setMsg("删除失败-" + e.getCause());
        }
    }

    @ApiOperation("根据id批量删除员工")
    @DeleteMapping
    private Result deletes(@RequestBody ArrayList<Long> ids) {
        try {
            service.removeAll(ids);
            return Result.me().setMsg("批量删除成功");
        } catch (Exception e) {
            return Result.me(false).setMsg("删除失败-" + e.getCause());
        }
    }

    @ApiOperation("分页查询员工")
    @PostMapping
    private Result findEmpByPage(@RequestBody EmployeeQuery query) {
        return Result.me(service.findByPage(query));
    }

    @ApiOperation("查询所有员工")
    @GetMapping
    private Result findAll() {
        return Result.me(service.findAllEmp());
    }

    @ApiOperation("修改员工角色")
    @PutMapping
    private Result changeRole(@RequestBody EmployeeQuery emp) {
        try {
            service.changeRole(emp);
            return Result.me().setMsg("修改成功");
        } catch (Exception e) {
            return Result.me(false).setMsg("修改失败-" + e.getCause());
        }
    }
}
