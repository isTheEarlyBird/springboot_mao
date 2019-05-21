package com.my.mao.service.impl;

import com.my.mao.dao.UserDao;
import com.my.mao.domain.User;
import com.my.mao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User checkExistsUser(String name, String password) throws Exception {
        return userDao.checkExistsUser(name, password);
    }

    @Override
    public void addUser(User user) throws Exception {
        userDao.addUser(user);
    }

    @Override
    public Long checkedUserExists(String name) throws Exception {
        return userDao.checkedUserExists(name);
    }
}
