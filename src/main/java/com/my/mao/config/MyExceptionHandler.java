package com.my.mao.config;

import com.my.mao.utils.RefererPathUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 自定义拦截，处理权限异常
 *
 * @author atp
 *
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = { AuthorizationException.class })
    public ModelAndView authorizationException(HttpServletRequest request) {

        //获取上一个路径
        String path = RefererPathUtils.getRefererPathUtils(request, "/admin");

        ModelAndView mv = new ModelAndView();
        mv.addObject("error","您没有权限");
        mv.addObject("path",path);
        mv.setViewName("admin/error");
        return mv;
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ModelAndView sQLIntegrityConstraintViolationException(HttpServletRequest request){
        //获取上一个路径
        String path = RefererPathUtils.getRefererPathUtils(request, "/admin");

        ModelAndView mv = new ModelAndView();
        mv.addObject("error","不好意思，有外键，不能删除，例如订单有该商品了");
        mv.addObject("path",path);
        mv.setViewName("admin/error");
        return mv;
    }
}