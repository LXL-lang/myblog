package com.lxl.service.impl;

import com.github.pagehelper.PageHelper;
import com.lxl.mapper.BlogMapper;
import com.lxl.mapper.TagMapper;
import com.lxl.pojo.Blog;
import com.lxl.pojo.Tag;


import com.lxl.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private BlogMapper blogMapper;
    @Override
    public List<Tag> getAllTagByPage(Integer pageNum, int i) {
        PageHelper.startPage(pageNum,i);
        List<Tag>  tagList= tagMapper.findAll();
        return tagList;
    }

    @Override
    public Tag getTagByName(String name) {
        return tagMapper.getTagByName(name);
    }
    @Transactional
    @Override
    public int saveTag(Tag tag) {
        return tagMapper.save(tag);
    }

    @Override
    public Tag getTagById(Long id) {
        return tagMapper.getTagById(id);
    }
    @Transactional
    @Override
    public int delete(Long id) {
        return tagMapper.delete(id);
    }
    @Transactional
    @Override
    public int updateTag(Long id, Tag tag) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("tag",tag);
        return tagMapper.updateTag(map);
    }

    @Override
    public List<Tag> findAll() {
        return tagMapper.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        List<Long> list= convertToList(ids);
        return tagMapper.findByIds(list);
    }
    //数量前几的标签
    @Override
    public List<Tag> listTagTop(int size) {
        List<Tag> list = tagMapper.findTop();
        for (Tag tag : list) {
            List<Blog> blogList = blogMapper.findByTagId(tag.getId());
            tag.setBlogs(blogList);
        }
        list.sort(new Comparator<Tag>() {
            @Override
            public int compare(Tag o1, Tag o2) {
               return    o2.getBlogs().size()-o1.getBlogs().size();
            }
        });
        if (list.size()<=size) return list;
        List<Tag> tagList=new ArrayList<>();
        for (int i = 0; i < size; i++) {
            tagList.add(list.get(i));
        }
        return tagList;
    }

    //将1，2，3这样字符串形式用，分割成数组转化为List
    private List<Long> convertToList(String ids) { //将字符串转换为一个数组list
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }
}
