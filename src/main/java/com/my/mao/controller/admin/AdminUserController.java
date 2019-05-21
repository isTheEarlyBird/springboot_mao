package com.my.mao.controller.admin;

import com.my.mao.domain.AdminUser;
import com.my.mao.service.AdminUserService;
import com.my.mao.utils.RefererPathUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 所有后台管理员都可以调用
 */

@Controller
@RequestMapping("/admin")
public class AdminUserController {


    @Autowired
    private AdminUserService adminUserService;

    /**
     * 登录
     * @param request
     * @return
     */
    @RequestMapping("/login.do")
    public ModelAndView login(String userName, String password, boolean rememberMe, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        if (rememberMe){
            token.setRememberMe(rememberMe);
        }
        try {
            subject.login(token);
            AdminUser adminUser = adminUserService.findUserByUserName(userName);
            request.getSession().setAttribute("adminUser", adminUser);
            mv.setViewName("redirect:/admin");
            return mv;
        } catch (UnknownAccountException e) {
            mv.addObject("error", "账号不存在！");
        } catch (IncorrectCredentialsException e) {
            mv.addObject("error", "账号/密码错误！");
        } catch (DisabledAccountException e) {
            mv.addObject("error", "账号已失效！");
        } catch (ExcessiveAttemptsException e) {
            mv.addObject("error", "您尝试次数过多！锁定1个小时!");
        } catch (Exception e){
            mv.addObject("error", "未知错误！");
        }

        //认证不成功跳转
        mv.setViewName("/admin/admin-login");
        return mv;
    }



    /**
     * 更改密码
     * @param password
     * @return
     */
    @RequestMapping("/updatePassword.do")
    @RequiresRoles("COMMON")
    public String updatePassword(String password, HttpServletRequest request, Model model){
        if (StringUtils.isEmpty(password)){
            model.addAttribute("error", "不能为空");
            return "admin/user/admin-updatePassword";
        }
        AdminUser adminUser = (AdminUser)request.getSession().getAttribute("adminUser");
        adminUserService.updatePassword(adminUser.getId(), new Md5Hash(password, adminUser.getUserName(), 3).toString());
        return "redirect:/admin";
    }
}
