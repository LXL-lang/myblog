package com.lxl.interceptor;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 关于web的配置类
 */
//WebMvcConfigurer里面有很多关于Web的控制方法
@Configuration
public class WebConfig implements WebMvcConfigurer {
    //关于拦截器的配置
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");


    }
}
