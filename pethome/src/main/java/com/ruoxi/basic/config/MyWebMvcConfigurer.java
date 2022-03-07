package com.ruoxi.basic.config;

import com.ruoxi.basic.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author 若兮
 * @create 2022-03-03 19:27
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/assets/**")
                .excludePathPatterns("/user/**")
                .excludePathPatterns("/shop/upload")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/basic/**")
                .excludePathPatterns("/images/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/*.html")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }
}
