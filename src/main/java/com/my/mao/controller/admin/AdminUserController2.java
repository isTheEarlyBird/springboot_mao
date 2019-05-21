package com.my.mao.controller.admin;

import com.my.mao.annotation.PermissionDesc;
import com.my.mao.domain.AdminUser;
import com.my.mao.domain.Permission;
import com.my.mao.domain.Role;
import com.my.mao.service.AdminUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 只有是ADMIN管理员才可以调用
 */

@Controller
@RequestMapping("/admin")
@RequiresRoles("ADMIN")
public class AdminUserController2 {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private RequestMappingHandlerMapping rmhm;

    /**
     * 重新自动向数据库保存权限
     * @return
     */
    @RequestMapping("/reload.do")
    public String reload(){

        //获取数据库中所有权限名
        List<String> permissionNameList = adminUserService.findAllPermissionName();

        //获取所有带@RequestMapping的方法
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = rmhm.getHandlerMethods();
        Collection<HandlerMethod> values = handlerMethods.values();

        //已添加的权限，防止有重复
        List<String> addedPermissionName = new LinkedList<>();

        for (HandlerMethod method : values) {
            //获取@RequiresPermissions注解
            RequiresPermissions annotation = method.getMethodAnnotation(RequiresPermissions.class);
            if (null != annotation){
                //获取权限名，因为所写的注解只有一个值
                String permissionName = annotation.value()[0];

                //数据库中没有这个权限，添加到数据库
                if ((!permissionNameList.contains(permissionName)) && (!addedPermissionName.contains(permissionName))){
                    //获取@PermissionDesc注解
                    String permissionDesc = method.getMethodAnnotation(PermissionDesc.class).value();
                    //标识已添加
                    addedPermissionName.add(permissionName);
                    Permission permission = new Permission();
                    permission.setPermissionName(permissionName);
                    permission.setDesc(permissionDesc);

                    adminUserService.insertPermission(permission);
                }
            }
        }

        return "redirect:/admin";
    }

    /**
     * 添加用户
     * @param adminUser
     * @return
     */
    @RequestMapping("/insertAdminUser.do")
    @RequiresPermissions("adminUser:insert")
    @PermissionDesc("添加后台用户")
    public ModelAndView insertAdminUser(AdminUser adminUser){
        ModelAndView mv = new ModelAndView();
        if (StringUtils.isEmpty(adminUser.getUserName()) || StringUtils.isEmpty(adminUser.getPassword())){
            mv.addObject("error", "不能为空");
            mv.setViewName("admin/user/admin-insertAdminUser");
            return mv;
        }

        //加密
        adminUser.setPassword(new Md5Hash(adminUser.getPassword(), adminUser.getUserName(), 3).toString());
        adminUserService.insertAdminUser(adminUser);

        mv.setViewName("redirect:/admin/findAllAdminUser.do");
        return mv;
    }

    /**
     * 跳转到修改用户界面
     * @param uid
     * @return
     */
    @RequestMapping("/toUpdateAdminUser.do")
    @RequiresPermissions("adminUser:update")
    @PermissionDesc("修改后台用户")
    public ModelAndView updateAdminUserJsp(String uid){
        //根据id获取用户
        AdminUser adminUser = adminUserService.findUserById(uid);
        ModelAndView mv = new ModelAndView();
        mv.addObject("adminUser", adminUser);
        mv.setViewName("admin/user/admin-updateAdminUser");
        return mv;
    }

    /**
     * 修改后台用户信息
     * @param adminUser
     * @return
     */
    @RequestMapping("/updateAdminUser.do")
    @RequiresPermissions("adminUser:update")
    @PermissionDesc("修改后台用户")
    public String updateAdminUser(AdminUser adminUser, Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (StringUtils.isEmpty(adminUser.getUserName())){
            model.addAttribute("error", "不能为空");
            request.getRequestDispatcher("/admin/toUpdateAdminUser.do?uid="+adminUser.getId()).forward(request, response);
            return null;
        }
        adminUserService.updateAdminUser(adminUser);

        return "redirect:/admin/findAllAdminUser.do";
    }
    
    /**
     * 查询所有管理用户
     * @return
     */
    @RequestMapping("/findAllAdminUser.do")
    @RequiresPermissions("adminUser:find")
    @PermissionDesc("查询后台用户")
    public ModelAndView findAllAdminUser(){
        List<AdminUser> adminUsers = adminUserService.findAllUser();

        ModelAndView mv = new ModelAndView();
        mv.addObject("adminUsers", adminUsers);
        mv.setViewName("admin/user/admin-users");
        return mv;
    }

    /**
     * 根据id查询用户
     * @param uid
     * @return
     */
    @RequestMapping("/findUserById.do")
    @RequiresPermissions("adminUser:find")
    @PermissionDesc("查询后台用户")
    public ModelAndView findUserById(String uid){
        AdminUser user = adminUserService.findUserById(uid);
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", user);
        mv.setViewName("admin/user/admin-userItem");
        return mv;
    }

    /**
     * 根据id查询角色
     * @param rid
     * @return
     */
    @RequestMapping("/findRoleById.do")
    @RequiresPermissions("adminRole:find")
    @PermissionDesc("查询角色")
    public ModelAndView findRoleById(String rid){
        Role role = adminUserService.findRoleById(rid);

        ModelAndView mv = new ModelAndView();
        mv.addObject("role", role);
        mv.setViewName("admin/user/role/admin-roleItem");
        return mv;
    }

    /**
     * 查询所有角色
     * @return
     */
    @RequestMapping("/findAllRole.do")
    @RequiresPermissions("adminRole:find")
    @PermissionDesc("查询角色")
    public ModelAndView findAllRole(){
        List<Role> roles = adminUserService.findAllRole();

        ModelAndView mv = new ModelAndView();
        mv.addObject("roles", roles);
        mv.setViewName("admin/user/role/admin-roles");
        return mv;
    }

    /**
     * 为用户添加（多个）角色
     * @param uid
     * @param ids   选择的角色id
     * @return
     */
    @RequestMapping("/insertRoleToUser.do")
    @RequiresPermissions("adminRole:save")
    @PermissionDesc("添加角色")
    public ModelAndView insertRoleToUser(String uid, String[] ids){
        ModelAndView mv;
        if (null != ids && ids.length > 0){
            adminUserService.insertRoleToUser(uid, ids);
            mv = new ModelAndView();
            mv.setViewName("redirect:/admin/findAllAdminUser.do");
        }else {
            mv = new ModelAndView("redirect:/admin/findOtherRole.do?uid="+uid+"&error=error");
        }
        return mv;
    }

    /**
     * 查询用户没有的角色
     * @param uid
     * @return
     */
    @RequestMapping("/findOtherRole.do")
    @RequiresPermissions("adminRole:find")
    @PermissionDesc("查询角色")
    public ModelAndView findOtherRole(String uid){
        List<Role> roles = adminUserService.findOtherRole(uid);

        ModelAndView mv = new ModelAndView();
        mv.addObject("roles", roles);
        mv.setViewName("admin/user/admin-insertRoleToUser");
        return mv;
    }

    /**
     * 添加新角色
     * @param role
     * @return
     */
    @RequestMapping("/insertRole.do")
    @RequiresPermissions("adminRole:save")
    @PermissionDesc("添加角色")
    public ModelAndView addRole(Role role){
        String roleName = role.getRoleName();
        String roleDesc = role.getRoleDesc();
        ModelAndView mv = new ModelAndView();
        if (null == roleName || null == roleDesc || "".equals(roleName) || "".equals(roleDesc)){
            mv.setViewName("/admin/user/role/admin-insertRole");
            mv.addObject("error", "不允许为空");
            return mv;
        }
        //查询数据库是否已经存在该角色
        if (null != adminUserService.findRoleByName(roleName)){
            mv.setViewName("/admin/user/role/admin-insertRole");
            mv.addObject("error", "角色已存在");
            return mv;
        }

        adminUserService.insertRole(role);
        mv.setViewName("redirect:/admin/findAllRole.do");
        return mv;
    }

    /**
     * 删除该用户的某个角色
     * @param uid
     * @param rid
     * @return
     */
    @RequestMapping("/deleteRoleByUid.do")
    @RequiresPermissions("adminRole:delete")
    @PermissionDesc("删除角色")
    public String deleteRoleByUid(@RequestParam(value = "uid",required = true) String uid, @RequestParam(value = "rid",required = true) String rid){
        adminUserService.deleteRoleByUid(uid, rid);

        return "redirect:/admin/findUserById.do?uid="+uid;
    }


    /**
     * 删除该角色的某个权限
     * @param rid
     * @param pid
     * @return
     */
    @RequestMapping("/deletePermissionByRid.do")
    @RequiresPermissions("adminPermission:delete")
    @PermissionDesc("删除角色")
    public String deletePermissionByRid(@RequestParam(value = "rid",required = true) String rid, @RequestParam(value = "pid",required = true) String pid){
        adminUserService.deletePermissionByRid(rid, pid);
        return "redirect:/admin/findRoleById.do?rid="+rid;
    }

    /**
     * 查询所有权限
     * @return
     */
    @RequestMapping("/findAllPermission.do")
    @RequiresPermissions("adminPermission:find")
    @PermissionDesc("查询权限")
    public ModelAndView findAllPermission(){
        List<Permission> permissions = adminUserService.findAllPermission();

        ModelAndView mv = new ModelAndView();
        mv.addObject("permissions", permissions);
        mv.setViewName("admin/user/permission/admin-permissions");
        return mv;
    }

    /**
     * 查询角色没有的权限
     * @param rid
     * @return
     */
    @RequestMapping("/findOtherPermission.do")
    @RequiresPermissions("adminPermission:find")
    @PermissionDesc("查询权限")
    public ModelAndView findOtherPermission(String rid){
        List<Permission> permissions = adminUserService.findOtherPermission(rid);

        ModelAndView mv = new ModelAndView();
        mv.addObject("permissions", permissions);
        mv.setViewName("admin/user/role/admin-insertPermissionToRole");
        return mv;
    }

    /**
     * 为角色添加权限
     * @param rid
     * @param ids  选择的权限
     * @return
     */
    @RequestMapping("/insertPermissionToRole.do")
    @RequiresPermissions("adminPermission:save")
    @PermissionDesc("添加权限")
    public ModelAndView insertPermissionToRole(String rid, String[] ids){
        ModelAndView mv;
        if (null != ids && ids.length > 0) {
            adminUserService.insertPermissionToRole(rid, ids);
            mv = new ModelAndView();
            mv.setViewName("redirect:/admin/findAllRole.do");
        }else {
            mv = new ModelAndView("redirect:/admin/findOtherPermission.do?rid="+rid+"&error=error");
        }
        return mv;
    }

    /**
     * 添加新权限
     * @return
     */
    @RequestMapping("/insertPermission.do")
    @RequiresPermissions("adminPermission:save")
    @PermissionDesc("添加权限")
    public ModelAndView insertPermission(Permission permission){
        String permissionName = permission.getPermissionName();
        String permissionDesc = permission.getDesc();
        ModelAndView mv = new ModelAndView();
        if (null == permissionName || null == permissionDesc || "".equals(permissionName) || "".equals(permissionDesc)){
            mv.setViewName("/admin/user/permission/admin-insertPermission");
            mv.addObject("error", "不允许为空");
            return mv;
        }
        //查询数据库是否已经存在该角色
        if (null != adminUserService.findPermissionByName(permissionName)){
            mv.setViewName("/admin/user/permission/admin-insertPermission");
            mv.addObject("error", "角色已存在");
            return mv;
        }

        adminUserService.insertPermission(permission);
        mv.setViewName("redirect:/admin/findAllPermission.do");
        return mv;
    }


}
