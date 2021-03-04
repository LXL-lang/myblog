package com.lxl.mapper;

import java.util.Date;

/**
 * @Author : lxl
 * @create : 2021/2/28 21:50
 * @describe: 访客数量
 */
public interface VisiteMapper {

    void add(Date date);
    Long findAll();
}
