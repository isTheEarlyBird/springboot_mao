package com.my.mao.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class shiroConfig {

    @Bean
    public AdminUserRealm adminUserRealm(){
        AdminUserRealm adminUserRealm = new AdminUserRealm();
        adminUserRealm.setCredentialsMatcher(myHashedCredentialsMatcher());
        return adminUserRealm;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    //权限管理
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(adminUserRealm());
        securityManager.setRememberMeManager(rememberMeManager());
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filterMap = new LinkedHashMap<>();

        //Spring Boot默认加载所有的Filter Bean并且在请求的时候都会生效，这样就导致每次请求LogoutFilter都会生效
        //自定义logout登出地址
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/admin/toLogin");
        filterMap.put("logout", logoutFilter);
        shiroFilterFactoryBean.setFilters(filterMap);

        //必须为LinkedHashMap 否则anon不生效
        Map<String, String> map = new LinkedHashMap<>();
        map.put("/admin/login.do", "anon");
        map.put("/admin/toLogin", "anon");
        map.put("/admin/logout.do", "logout");
        map.put("/admin/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        shiroFilterFactoryBean.setLoginUrl("/admin/toLogin");
        shiroFilterFactoryBean.setSuccessUrl("/admin");


        return shiroFilterFactoryBean;
    }


    //设置加密方式
    @Bean
    public MyHashedCredentialsMatcher myHashedCredentialsMatcher(){
        MyHashedCredentialsMatcher myHashedCredentialsMatcher = new MyHashedCredentialsMatcher(ehCacheManager());
        // 使用md5 算法进行加密
        myHashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 设置散列次数： 意为加密几次
        myHashedCredentialsMatcher.setHashIterations(3);

        return myHashedCredentialsMatcher;
    }

    //缓存
    @Bean
    public EhCacheManager ehCacheManager(){
        EhCacheManager ehcacheManager = new EhCacheManager();
        ehcacheManager.setCacheManagerConfigFile("classpath:config/shiro-ehcache.xml");
        return ehcacheManager;
    }

    //shiro注解
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     *Cookie
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //如果httyOnly设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
        simpleCookie.setHttpOnly(true);
        //记住我cookie生效时间,单位是秒
        simpleCookie.setMaxAge(60*5);
        return simpleCookie;
    }

    /**
     * cookie管理器;
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();

        //加密秘钥
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

}
