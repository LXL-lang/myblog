package com.lxl.service;

/**
 * @Author : lxl
 * @create : 2021/2/28 21:47
 * @describe: 记录访客数量
 */
public interface VisiteService {
    void increase();
    Long findAllVisite();
}
