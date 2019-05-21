package com.my.mao.dao;

import com.my.mao.domain.AdminUser;
import com.my.mao.domain.Permission;
import com.my.mao.domain.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface AdminDao {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "userName", property = "userName"),
            @Result(column = "password", property = "password"),
            @Result(column = "status", property = "status"),
            @Result(column = "id", property = "roles", javaType = List.class, many = @Many(select = "com.my.mao.dao.AdminDao.findRoleByUid")),
    })
    @Select("select * from admin_user where userName = #{userName}")
    AdminUser findUserByUserName(String username);

    /**
     * 查询所有管理用户
     * @return
     */
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "userName", property = "userName"),
            @Result(column = "password", property = "password"),
            @Result(column = "status", property = "status"),
    })
    @Select("select * from admin_user")
    List<AdminUser> findAllUser();

    /**
     * 根据id查询管理用户
     * @return
     */
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "userName", property = "userName"),
            @Result(column = "password", property = "password"),
            @Result(column = "status", property = "status"),
            @Result(column = "id", property = "roles", javaType = List.class, many = @Many(select = "com.my.mao.dao.AdminDao.findRoleByUid")),
    })
    @Select("select * from admin_user where id = #{uid}")
    AdminUser findUserById(String uid);

    /**
     * 根据uid查询角色
     * @param uid
     * @return
     */
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "roleName", property = "roleName"),
            @Result(column = "roleDesc", property = "roleDesc"),
            @Result(column = "id", property = "permissions", javaType = List.class,many = @Many(select = "com.my.mao.dao.AdminDao.findPermissionByRid"))
    })
    @Select("select * from role where id in(select roleId from user_role where userId = #{uid})")
    List<Role> findRoleByUid(String uid);

    /**
     * 根据rid查询权限
     * @param rid
     * @return
     */
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "permissionName", property = "permissionName"),
            @Result(column = "desc", property = "desc")
    })
    @Select("select * from permission where id in(select permissionId from role_permission where roleId = #{rid})")
    List<Permission> findPermissionByRid(String rid);

    /**
     * 查询用户没有的角色
     * @param uid
     * @return
     */
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "roleName", property = "roleName"),
            @Result(column = "roleDesc", property = "roleDesc"),
    })
    @Select("select * from role where not id in(select roleId from user_role where userId = #{uid})")
    List<Role> findOtherRole(String uid);

    /**
     * 为用户添加（多个）角色
     * @param uid
     * @param rid   选择的角色id
     * @return
     */
    @Update("insert into user_role values(#{uid}, #{rid})")
    void insertRoleToUser(@Param("uid") String uid, @Param("rid") String rid);

    /**
     * 查询所有角色
     * @return
     */
    @Select("select * from role")
    List<Role> findAllRole();

    /**
     * 查询角色没有的权限
     * @param rid
     * @return
     */
    @Select("select * from permission where id not in(select permissionId from role_permission where roleId = #{rid}) ")
    List<Permission> findOtherPermission(String rid);

    /**
     * 为角色添加权限
     * @param rid
     * @param pid  选择的权限
     * @return
     */
    @Insert("insert into role_permission values(#{rid}, #{pid})")
    void insertPermissionToRole(@Param("rid") String rid, @Param("pid") String pid);

    /**
     * 根据id查询角色
     * @param rid
     * @return
     */
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "roleName", property = "roleName"),
            @Result(column = "roleDesc", property = "roleDesc"),
            @Result(column = "id", property = "permissions", javaType = List.class,many = @Many(select = "com.my.mao.dao.AdminDao.findPermissionByRid"))
    })
    @Select("select * from role where id = #{rid}")
    Role findRoleById(String rid);

    @Select("select * from permission")
    List<Permission> findAllPermission();

    /**
     * 添加新用户
     * @param adminUser
     */
    @Insert("insert into admin_user values(replace(UUID(), '-', ''), #{userName}, #{password}, 1)")
    void insertAdminUser(AdminUser adminUser);

    /**
     * 添加新权角色
     * @return
     */
    @Insert("insert into role values(replace(UUID(), '-', ''), #{roleName}, #{roleDesc})")
    void insertRole(Role role);

    /**
     * 添加新权限
     * @return
     */
    @Insert("insert into permission values(replace(UUID(), '-', ''), #{permissionName}, #{desc})")
    void insertPermission(Permission permission);

    /**
     * 删除该用户的某个角色
     * @param uid
     * @param rid
     * @return
     */
    @Delete("delete from user_role where userId = #{uid} and roleId = #{rid}")
    void deleteRoleByUid(@Param("uid") String uid, @Param("rid") String rid);

    /**
     * 删除该角色的某个权限
     * @param rid
     * @param pid
     * @return
     */
    @Delete("delete from role_permission where roleId = #{rid} and permissionId = #{pid}")
    void deletePermissionByRid(@Param("rid") String rid, @Param("pid") String pid);

    /**
     * 获取所有权限名
     * @return
     */
    @Select("select permissionName from permission")
    List<String> findAllPermissionName();

    /**
     * 修改后台用户信息
     * @param adminUser
     */
    @Update("update admin_user set userName= #{userName}, status = ${status} where id = #{id}")
    void updateAdminUser(AdminUser adminUser);

    /**
     * 更改密码
     * @param uid
     * @param password
     */
    @Update("update admin_user set password = #{password} where id = #{uid}")
    void updatePassword(@Param("uid") String uid, @Param("password") String password);

    /**
     * 根据角色名查询角色
     * @param roleName
     * @return
     */
    @Select("select * from role where roleName = #{roleName}")
    Role findRoleByName(String roleName);

    /**
     * 根据权限名查询权限
     * @param permissionName
     * @return
     */
    @Select("select * from permission where permissionName = #{permissionName}")
    Permission findPermissionByName(String permissionName);
}
