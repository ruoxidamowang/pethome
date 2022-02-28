package com.ruoxi.gm.controller;

import com.ruoxi.basic.common.Result;
import com.ruoxi.gm.service.IEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @ApiOperation("查询所有员工信息")
    @GetMapping
    private Result findByPage() {
        return Result.me(service.findAllEmp());
    }
}
