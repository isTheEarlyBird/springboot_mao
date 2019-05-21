package com.my.mao.controller.user;

import com.my.mao.domain.User;
import com.my.mao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param name   用户名
     * @param password   用户密码
     * @param request
     * @return
     */
    @PostMapping("/login.do")
    public ModelAndView login(String name, String password, HttpServletRequest request) throws Exception {
        ModelAndView mv = null;
        User user = userService.checkExistsUser(name, password);
        if (null == user){
            mv = new ModelAndView();
            mv.addObject("error", "用户或密码错误");
            mv.setViewName("/user/login");
        }else {
            request.getSession().setAttribute("user", user);
            mv = new ModelAndView("redirect:/index/init.do");
        }
        return mv;
    }

    /**
     * 用户登出
     * @param request
     * @return
     */
    @RequestMapping("/logout.do")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        //移除购物车
        session.removeAttribute("cart");
        return "redirect:/index/init.do";
    }


    /**
     * 根据用户名查询 用户名是否已存在
     * @param name
     * @param response
     * @throws Exception
     */
    @RequestMapping("/checkedUserExists.do")
    @ResponseBody
    public String checkedUserExists(@RequestBody String name, HttpServletResponse response) throws Exception {
        Long num = userService.checkedUserExists(name.split("=")[1]);
        if (num > 0) {
            return "false";
        } else {
            return "true";
        }
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register.do")
    public String register(User user) throws Exception {
        user.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));

        userService.addUser(user);

        return "redirect:/user/toLogin";
    }

}
