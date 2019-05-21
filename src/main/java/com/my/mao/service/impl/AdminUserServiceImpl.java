package com.my.mao.service.impl;

import com.my.mao.dao.AdminDao;
import com.my.mao.dao.OrderDao;
import com.my.mao.domain.AdminUser;
import com.my.mao.domain.Permission;
import com.my.mao.domain.Role;
import com.my.mao.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public List<AdminUser> findAllUser() {
        return adminDao.findAllUser();
    }

    @Override
    public AdminUser findUserById(String uid) {
        return adminDao.findUserById(uid);
    }

    @Override
    public AdminUser findUserByUserName(String username) {
        return adminDao.findUserByUserName(username);
    }

    @Override
    public List<Role> findOtherRole(String uid) {
        return adminDao.findOtherRole(uid);
    }

    @Override
    public void insertRoleToUser(String uid, String[] ids) {
        for (String rid : ids) {
            adminDao.insertRoleToUser(uid, rid);
        }
    }

    @Override
    public List<Role> findAllRole() {
        return adminDao.findAllRole();
    }

    @Override
    public List<Permission> findOtherPermission(String rid) {
        return adminDao.findOtherPermission(rid);
    }

    @Override
    public void insertPermissionToRole(String rid, String[] ids) {
        for (String pid : ids) {
            adminDao.insertPermissionToRole(rid, pid);
        }
    }

    @Override
    public Role findRoleById(String rid) {
        return adminDao.findRoleById(rid);
    }

    @Override
    public List<Permission> findAllPermission() {
        return adminDao.findAllPermission();
    }

    @Override
    public List<String> findAllPermissionName() {
        return adminDao.findAllPermissionName();
    }

    @Override
    public void insertAdminUser(AdminUser adminUser) {
        adminDao.insertAdminUser(adminUser);
    }

    @Override
    public void insertRole(Role role) {
        adminDao.insertRole(role);
    }

    @Override
    public void insertPermission(Permission permission) {
        adminDao.insertPermission(permission);
    }

    @Override
    public void deleteRoleByUid(String uid, String rid) {
        adminDao.deleteRoleByUid(uid, rid);
    }

    @Override
    public void deletePermissionByRid(String rid, String pid) {
        adminDao.deletePermissionByRid(rid, pid);
    }

    @Override
    public void updateAdminUser(AdminUser adminUser) {
        adminDao.updateAdminUser(adminUser);
    }

    @Override
    public void updatePassword(String uid, String password) {
        adminDao.updatePassword(uid, password);
    }

    @Override
    public Role findRoleByName(String roleName) {
        return adminDao.findRoleByName(roleName);
    }

    @Override
    public Permission findPermissionByName(String permissionName) {
        return adminDao.findPermissionByName(permissionName);
    }


}
