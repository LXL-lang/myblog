package com.lxl.service.impl;

import com.lxl.mapper.UserMapper;
import com.lxl.pojo.User;
import com.lxl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User checkUser(String name, String password) {
        User user = userMapper.findByUsernameAndPassword(name, password);
        return user;
    }
}
