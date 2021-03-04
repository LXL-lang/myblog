package com.lxl.mapper;

import com.lxl.pojo.Type;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

public interface TypeMapper {



    List<Type> findAll();

    int save(Type type);

    Type getTypeByName(String name);

    Type getTypeById(Long id);

    int updateType(Map<String,Object> map);

    int delete(Long id);

    List<Type> findTop();
}
