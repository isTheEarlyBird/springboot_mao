package com.my.mao.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    //所有组件起作用
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new MyMvcConfig(){
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/user/toLogin").setViewName("/user/login");
                registry.addViewController("/user/toRegister").setViewName("/user/register");
                registry.addViewController("/user/permission/cart").setViewName("/user/permission/cart");
                registry.addViewController("/user/permission/orderinfo").setViewName("/user/permission/orderinfo");
                registry.addViewController("/user/permission/pay").setViewName("/user/permission/pay");
                registry.addViewController("/admin").setViewName("/admin/admin-index");
                registry.addViewController("/admin/toLogin").setViewName("/admin/admin-login");
                registry.addViewController("/admin/user/admin-insertAdminUser").setViewName("/admin/user/admin-insertAdminUser");
                registry.addViewController("/admin/user/role/insertRole").setViewName("/admin/user/role/admin-insertRole");
                registry.addViewController("/admin/user/permissions/admin-permissions").setViewName("/admin/user/permissions/admin-permissions");
                registry.addViewController("/admin/user/permission/admin-insertPermission").setViewName("/admin/user/permission/admin-insertPermission");
                registry.addViewController("/admin/user/updatePassword").setViewName("/admin/user/admin-updatePassword");
                registry.addViewController("/admin/product/admin-categorys").setViewName("/admin/product/admin-categorys");
                registry.addViewController("/admin/product/admin-insertCategory").setViewName("/admin/product/admin-insertCategory");
                registry.addViewController("/admin/product/admin-products").setViewName("/admin/product/admin-products");
                registry.addViewController("/admin/insertPermissionToRole").setViewName("/admin/role/admin-insertPermissionToRole");
                registry.addViewController("/admin/order/admin-unprocessed-order").setViewName("/admin/order/admin-unprocessed-order");
                registry.addViewController("/admin/order/admin-unprocessed-order").setViewName("/admin/order/admin-unprocessed-order");
            }

            //可以使用static路径
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry){
                registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/user/permission/**");
            }
        };
    }
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
