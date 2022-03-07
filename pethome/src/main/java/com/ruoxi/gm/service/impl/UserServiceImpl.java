package com.ruoxi.gm.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoxi.basic.common.*;
import com.ruoxi.gm.domain.LoginInfo;
import com.ruoxi.gm.mapper.LoginInfoMapper;
import com.ruoxi.gm.mapper.UserMapper;
import com.ruoxi.gm.query.UserQuery;
import com.ruoxi.gm.service.IUserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author 若兮
 * @create 2022-03-02 20:37
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper mapper;

    @Resource
    private LoginInfoMapper loginInfoMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private EmailUtils emailUtils;

    /**
     * @param phone 根据手机号查询是否已经注册过，注册过则不返回验证码，未注册则返回四位随机数字验证码
     * @return 返回信息和生成的验证码
     */
    @Override
    public Result getPhoneCode(String phone) {
        //查询手机号是否已经注册过
        LoginInfo userByPhone = loginInfoMapper.selectUserByPhone(phone);
        if (userByPhone != null) {
            //注册过则返回提示信息：您已注册，快去登录吧！
            return Result.me(false).setMsg("您已注册，快去登录吧！");
        } else {
            //未注册则查看之前是否已经生成过验证码
            String redisCode = (String) redisTemplate.opsForValue().get(phone);
            if (redisCode != null) {
                //如果生成过验证码则查看上次生成的验证码是否过期
                Long time = redisTemplate.getExpire(phone);
                //上次验证码未过期
                if (time > 0) {
                    //从redis中取出并再次发送短信
                    String sms = SMSUtils.sendSMS(phone, redisCode);
                    return Integer.parseInt(sms) > 0 ? Result.me().setMsg("验证码已发送至您的手机，请注意查收！") : Result.me(false).setMsg("验证码发送失败，请检查手机号是否正确");
                } else {
                    //上次验证码已过期，重新生成验证码
                    String code = RandomUtil.randomNumbers(4);
                    //存入redis
                    redisTemplate.opsForValue().set(phone, code);
                    //设置3分钟过期时间
                    redisTemplate.expire(phone, 3, TimeUnit.MINUTES);
                    //发送短信
                    String sms = SMSUtils.sendSMS(phone, code);
                    return Integer.parseInt(sms) > 0 ? Result.me().setMsg("验证码已发送至您的手机，请注意查收！") : Result.me(false).setMsg("验证码发送失败，请检查手机号是否正确");
                }
            } else {
                //未生成过则生成验证码
                String code = RandomUtil.randomNumbers(4);
                //存入redis
                redisTemplate.opsForValue().set(phone, code);
                //重新生成验证码存入redis并设置3分钟过期时间
                redisTemplate.expire(phone, 3, TimeUnit.MINUTES);
                //发送短信
                String sms = SMSUtils.sendSMS(phone, code);
                return Integer.parseInt(sms) > 0 ? Result.me().setMsg("验证码已发送至您的手机，请注意查收！") : Result.me(false).setMsg("验证码发送失败，请检查手机号是否正确");
            }
        }
    }

    /**
     * 注册
     *
     * @param query 用户信息
     * @return 返回注册结果
     */
    @Override
    @Transactional
    public Result register(UserQuery query) {
        //判空验证
        if (!StrUtil.hasEmpty(query.getPhone(), query.getPassword(), query.getRepassword()) && query.getCode() != null) {
            //判断验证码是否正确
            String redisCode = (String) redisTemplate.opsForValue().get(query.getPhone());
            if (!query.getCode().toString().equals(redisCode)) {
                //验证码不正确
                return Result.me(false).setMsg("验证码输入错误或验证码已超时");
            }
            //判断密码与重复密码是否一致
            if (!query.getPassword().equals(query.getRepassword())) {
                return Result.me(false).setMsg("密码与确认密码不一致，请重新输入");
            }
            //所有信息校验通过
            //对密码进行加密
            String salt = RandomUtil.randomString(32);
            query.setSalt(salt);
            query.setPassword(MD5Utils.encrypByMd5(query.getPassword() + Constant.PRE_SALT + salt + Constant.SUF_SALT));
            query.setDisable(1);
            query.setType(1);
            query.setUsername(IdUtil.simpleUUID());
            //将数据存入数据库
            loginInfoMapper.save(query);
            query.setState(1);
            mapper.save(query);
            return Result.me().setMsg("注册成功！快去登录吧！");
        }
        return Result.me(false).setMsg("请输入正确的登录信息");
    }

    /**
     * 登录接口
     *
     * @param query 登录信息，用户名和密码
     * @return 登陆结果
     */
    @Override
    public Result login(UserQuery query) {
        UserQuery info = loginInfoMapper.findByUsernameOrPhoneOrEmail(query);
        if (info != null && MD5Utils.encrypByMd5(query.getPassword() + Constant.PRE_SALT + info.getSalt() + Constant.SUF_SALT).equals(info.getPassword())) {
            String uuid = IdUtil.simpleUUID();
            redisTemplate.opsForValue().set(uuid, query.getUsername());
            redisTemplate.expire(uuid, 30, TimeUnit.MINUTES);
            info.setToken(uuid);
            System.out.println(info);
            return Result.me(info);
        } else {
            return Result.me(false).setMsg("用户名或密码错误");
        }


    }

    /**
     * @param email 根据邮箱，注册过则不返回验证码，未注册则返回四位随机数字验证码
     * @return 返回生成的验证码
     */
    @Override
    public Result getEmailCode(String email) {
        //查询邮箱是否已经注册过
        LoginInfo userByEmail = loginInfoMapper.selectUserByEmail(email);
        if (userByEmail != null) {
            //注册过则返回提示信息：您已注册，快去登录吧！
            return Result.me(false).setMsg("您已注册，快去登录吧！");
        } else {
            //未注册则查看之前是否已经生成过验证码
            String redisCode = (String) redisTemplate.opsForValue().get(email);
            if (redisCode != null) {
                //如果生成过验证码则查看上次生成的验证码是否过期
                Long time = redisTemplate.getExpire(email);
                //上次验证码未过期
                if (time > 0) {
                    //从redis中取出并再次发送邮件
                    emailUtils.sendEmail(email, redisCode);
                } else {
                    //上次验证码已过期，重新生成验证码
                    String code = RandomUtil.randomNumbers(4);
                    //存入redis
                    redisTemplate.opsForValue().set(email, code);
                    //设置3分钟过期时间
                    redisTemplate.expire(email, 3, TimeUnit.MINUTES);
                    //发送邮件
                    emailUtils.sendEmail(email, code);
                }
            } else {
                //未生成过则生成验证码
                String code = RandomUtil.randomNumbers(4);
                //存入redis
                redisTemplate.opsForValue().set(email, code);
                //重新生成验证码存入redis并设置3分钟过期时间
                redisTemplate.expire(email, 3, TimeUnit.MINUTES);
                //发送邮件
                emailUtils.sendEmail(email, code);
            }
            return Result.me().setMsg("验证码已发送至您的邮箱，请注意查收！");
        }
    }
}
