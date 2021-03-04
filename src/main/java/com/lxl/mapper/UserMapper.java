package com.lxl.mapper;

import com.lxl.pojo.User;

public interface UserMapper {
    public User findByUsernameAndPassword(String username, String password);
}
