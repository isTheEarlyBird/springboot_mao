package com.my.mao.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 后台管理用户表
 */
@Data
public class AdminUser implements Serializable {

    private String id; //id
    private String userName; //用户名
    private String password; // 密码
    private Integer status; //状态，0为用户被禁用了，1为用户可用
    private String statusStr;
    private List<Role> roles; // 该用户所拥有的角色

    public String getStatusStr() {
        if (status == 0){
            statusStr = "用户不可用";
        }else {
            statusStr = "用户可用";
        }
        return statusStr;
    }

}
