package com.ruoxi.gm.mapper;

import com.ruoxi.gm.domain.LoginInfo;
import com.ruoxi.gm.query.UserQuery;

/**
 * @author 若兮
 * @create 2022-03-03 9:34
 */
public interface LoginInfoMapper {
    void save(UserQuery query);

    LoginInfo selectUserByPhone(String phone);

    UserQuery findByUsernameOrPhoneOrEmail(UserQuery query);

    LoginInfo selectUserByEmail(String email);
}
