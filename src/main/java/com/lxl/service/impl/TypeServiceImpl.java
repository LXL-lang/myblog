package com.lxl.service.impl;

import com.github.pagehelper.PageHelper;
import com.lxl.mapper.BlogMapper;
import com.lxl.mapper.TypeMapper;
import com.lxl.pojo.Blog;
import com.lxl.pojo.Type;
import com.lxl.service.BlogService;
import com.lxl.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    BlogMapper blogMapper;
    @Override
    public List<Type> getAllTypeByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Type> typeList = typeMapper.findAll();
        return typeList;
    }

    @Override
    public List<Type> findAll() {
        return typeMapper.findAll();
    }

    public List<Type> getAllType(){
       return typeMapper.findAll();
    }
    @Transactional
    @Override
    public int saveType(Type type) {
        return typeMapper.save(type);
   }

    @Override
    public Type getTypeByName(String name) {

        return typeMapper.getTypeByName(name);
    }

    @Override
    public Type getTypeById(Long id) {
        return typeMapper.getTypeById(id);

    }
    @Transactional
    @Override
    public int updateType(Long id, Type type) {
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        map.put("type",type);
        return typeMapper.updateType(map);
    }
    @Transactional
    @Override
    public int delete(Long id) {
       return typeMapper.delete(id);
    }

    //查询前几条的数据,并按照数量排序
    @Override
    public List<Type> listTypeTop(int size) {
        List<Type> list = typeMapper.findTop();
        for (Type type : list) {
            List<Blog> blogList = blogMapper.findByTypeId(type.getId());
            type.setBlogs(blogList);
        }
        list.sort(new Comparator<Type>() {
            @Override
            public int compare(Type o1, Type o2) {
                return o2.getBlogs().size()-o1.getBlogs().size();
            }
        });
        if (list.size()<=size) return list;
        ArrayList<Type>  listType=new ArrayList<>();
        for (int i = 0; i < size; i++) {
            listType.add(list.get(i));
        }
        return listType;
    }

}
