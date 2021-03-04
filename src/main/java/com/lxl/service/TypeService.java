package com.lxl.service;

import com.lxl.pojo.Type;

import java.util.List;

public interface TypeService {
    //所有的Type,分页
   List<Type> getAllTypeByPage(Integer pageNum, Integer pageSize);
   //查到所有的Type，不分页
    List<Type> findAll();

    //新增类型
    int saveType(Type type);
    //通过名称查type
    Type getTypeByName(String name);

    Type getTypeById(Long id);

    int updateType(Long id, Type type);

    int delete(Long id);
    //查询前size条的type用于首页展示
    List<Type> listTypeTop(int size);


}
