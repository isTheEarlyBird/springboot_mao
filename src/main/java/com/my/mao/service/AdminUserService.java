package com.my.mao.service;

import com.my.mao.domain.AdminUser;
import com.my.mao.domain.Permission;
import com.my.mao.domain.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AdminUserService{

    /**
     * 添加新用户
     */
    void insertAdminUser(AdminUser adminUser);


    /**
     * 查询所有管理用户
     * @return
     */
    List<AdminUser> findAllUser();

    /**
     * 根据用户id查询用户
     * @param uid
     * @return
     */
    AdminUser findUserById(String uid);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    AdminUser findUserByUserName(String username);

    /**
     * 查询用户没有的角色
     * @param uid
     * @return
     */
    List<Role> findOtherRole(String uid);

    /**
     * 为用户添加（多个）角色
     * @param uid
     * @param ids   选择的角色id
     * @return
     */
    void insertRoleToUser(String uid, String[] ids);

    /**
     * 查询所有角色
     * @return
     */
    List<Role> findAllRole();

    /**
     * 查询角色没有的权限
     * @param rid
     * @return
     */
    List<Permission> findOtherPermission(String rid);

    /**
     * 为角色添加权限
     * @param rid
     * @param ids  选择的权限
     * @return
     */
    void insertPermissionToRole(String rid, String[] ids);

    /**
     * 根据id查询角色
     * @param rid
     * @return
     */
    Role findRoleById(String rid);

    /**
     * 查询所有权限
     * @return
     */
    List<Permission> findAllPermission();

    /**
     * 查询所有权限名
     * @return
     */
    List<String> findAllPermissionName();


    /**
     * 添加新角色
     * @return
     */
    void insertRole(Role role);

    /**
     * 添加新权限
     * @return
     */
    void insertPermission(Permission permission);

    /**
     * 删除该用户的某个角色
     * @param uid
     * @param rid
     * @return
     */
    void deleteRoleByUid(String uid, String rid);

    /**
     * 删除该角色的某个权限
     * @param rid
     * @param pid
     * @return
     */
    void deletePermissionByRid(String rid, String pid);


    /**
     * 修改后台用户信息
     * @param adminUser
     */
    void updateAdminUser(AdminUser adminUser);

    /**
     * 更改密码
     * @param uid
     * @param password
     * @return
     */
    void updatePassword(String uid, String password);

    /**
     * 根据角色名查询角色
     * @param roleName
     * @return
     */
    Role findRoleByName(String roleName);

    /**
     * 根据权限名查询权限
     * @param permissionName
     * @return
     */
    Permission findPermissionByName(String permissionName);
}
