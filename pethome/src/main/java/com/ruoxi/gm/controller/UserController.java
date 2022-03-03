package com.ruoxi.gm.controller;

import com.ruoxi.basic.common.Result;
import com.ruoxi.gm.query.UserQuery;
import com.ruoxi.gm.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 若兮
 * @create 2022-03-02 20:21
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
public class UserController {
    @Resource
    private IUserService service;

    /**
     * @param phone 根据手机号查询是否已经注册过，注册过则不返回验证码，未注册则返回四位随机数字验证码
     * @return 返回生成的验证码
     */
    @GetMapping("code/{phone}")
    @ApiOperation("根据手机号查询是否已经注册过，注册过则不返回验证码，未注册则返回四位随机数字验证码")
    public Result getCode(@PathVariable String phone){
        return service.getCode(phone);
    }

    @PostMapping("register")
    @ApiOperation("用户注册接口")
    public Result register(@RequestBody UserQuery query){
        return service.register(query);
    }

    @PostMapping("login")
    @ApiOperation("用户登录接口")
    public Result login(@RequestBody UserQuery query){
        return service.login(query);
    }
}
