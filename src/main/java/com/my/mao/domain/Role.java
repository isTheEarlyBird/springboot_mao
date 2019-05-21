package com.my.mao.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色表
 */
@Data
public class Role implements Serializable {

    public static final String ADMIN = "ADMIN";
    public static final String COMMON = "COMMON";

    private String id; // id
    private String roleName; // 角色名
    private String roleDesc; //角色描述
    private List<User> users; // 拥有该角色的用户
    private List<Permission> permissions;// 该角色所拥有的权限

}
