package com.my.mao.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 资源权限表
 */
@Data
public class Permission implements Serializable {

    private String id; // id
    private String permissionName;//权限名
    private String desc; // 资源地址
    private List<Role> roles;// 拥有该资源的角色

    public String getId() {
        return id;
    }

}
