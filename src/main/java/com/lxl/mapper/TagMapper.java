package com.lxl.mapper;

import com.lxl.pojo.Tag;
import com.lxl.pojo.Type;

import java.util.List;
import java.util.Map;

public interface TagMapper {
    List<Tag> findAll();
    int updateTag(Map<String,Object> map);
    int save(Tag tag);
    Tag getTagByName(String name);
    Tag getTagById(Long id);
    int delete(Long id);

    List<Tag> findByIds(List<Long> list);

    List<Tag> findTop( );
}
