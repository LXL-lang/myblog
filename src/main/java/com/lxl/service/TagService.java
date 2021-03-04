package com.lxl.service;

import com.lxl.pojo.Tag;
import com.lxl.pojo.Type;
import org.springframework.stereotype.Service;


import java.util.List;

public interface TagService {
    List<Tag> getAllTagByPage(Integer pageNum, int i);

    Tag getTagByName(String name);

    int saveTag(Tag tag);

    Tag getTagById(Long id);

    int delete(Long id);

    int updateTag(Long id, Tag tag);

    List<Tag> findAll();
    List<Tag> listTag(String ids);
    //前几个标签
    List<Tag> listTagTop(int size);
}
