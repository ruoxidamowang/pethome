package com.ruoxi.basic.interceptor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author 若兮
 * @create 2022-03-03 19:13
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("X-token");
        if (token != null && redisTemplate.opsForValue().get(token) != null) {
            redisTemplate.expire(token, 30, TimeUnit.MINUTES);
            return true;
        } else {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("请登录！");
            return false;
        }
    }
}
