package com.ruoxi.gm.service;

import com.ruoxi.basic.common.Result;
import com.ruoxi.gm.query.UserQuery;

/**
 * @author 若兮
 * @create 2022-03-02 20:36
 */
public interface IUserService {
    /**
     * 获取验证码
     * @param phone 根据手机号查询是否已经注册过，注册过则不返回验证码，未注册则返回四位随机数字验证码
     * @return 返回生成的验证码
     */
    Result getPhoneCode(String phone);

    /**
     * 注册
     * @param query 用户信息
     * @return 返回注册结果
     */
    Result register(UserQuery query);

    /**
     * 登录接口
     * @param query 登录信息，用户名和密码
     * @return 登陆结果
     */
    Result login(UserQuery query);

    /**
     * @param email 根据邮箱，注册过则不返回验证码，未注册则返回四位随机数字验证码
     * @return 返回生成的验证码
     */
    Result getEmailCode(String email);
}
