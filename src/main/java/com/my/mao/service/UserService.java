package com.my.mao.service;

import com.my.mao.domain.User;

public interface UserService {

    /**
     * 登录操作 查询用户是否存在
     * @param name   用户名
     * @param password   用户密码
     * @return
     */
    public User checkExistsUser(String name, String password) throws Exception;

    /**
     * 注册用户
     * @param user
     */
    void addUser(User user) throws Exception;

    /**
     * 根据用户名查询 用户名是否已存在
     * @param name
     */
    Long checkedUserExists(String name) throws Exception;
}
