package com.my.mao.config;

import com.my.mao.domain.AdminUser;
import com.my.mao.domain.Permission;
import com.my.mao.domain.Role;
import com.my.mao.service.AdminUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.CachingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

public class AdminUserRealm extends AuthorizingRealm {

    @Autowired
    private AdminUserService adminUserService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AdminUser adminUser = (AdminUser) principalCollection.getPrimaryPrincipal();

        List<String> roleList = new LinkedList<>();
        List<String> permissionList = new LinkedList<>();

        List<Role> roles = adminUser.getRoles();
        if (null == roles){
            return null;
        }else {
            List<Permission> permissions = null;
            for (Role role : roles) {
                roleList.add(role.getRoleName());
                permissions = role.getPermissions();
                if (null != permissions){
                    for (Permission permission : permissions) {
                        permissionList.add(permission.getPermissionName());
                    }
                }
            }

            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRoles(roleList);
            info.addStringPermissions(permissionList);
            return info;
        }

    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        AdminUser adminUser = adminUserService.findUserByUserName(username);
        //没有该用户
        if (null == adminUser){
            return null;
        }
        //账号失效
        if(adminUser.getStatus() == 0){
            throw new DisabledAccountException();
        }

        return new SimpleAuthenticationInfo(adminUser,adminUser.getPassword(), ByteSource.Util.bytes(username),this.getName());
    }
}