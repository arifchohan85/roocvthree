package com.egeroo.roocvthree.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.egeroo.roocvthree.core.error.CoreHandlerInterceptor;

@Configuration  
public class AppConfig implements WebMvcConfigurer  {  

    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(new CoreHandlerInterceptor());
    }
} 
