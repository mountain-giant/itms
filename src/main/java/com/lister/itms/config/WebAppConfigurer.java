package com.lister.itms.config;

import com.lister.itms.interceptors.LoginInterceptor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * Describe :
 * Created by Lister on 2018/7/16 3:55 PM.
 * Version : 1.0
 */
@Component
public class WebAppConfigurer implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册自定义拦截器，添加拦截路径和排除拦截路径                                                 
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/admin/login","/toLogin","/adminlte/**","/custom/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/sys/index");
        registry.addViewController("/error/404").setViewName("forward:/sys/index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

}
