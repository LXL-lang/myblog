package com.lxl.mapper;

import com.lxl.pojo.Blog;
import com.lxl.vo.BlogQuery;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BlogMapper {
    int deleteById(Long id);
    //新增博客
    int save(Blog blog);
    Blog getBlogById(Long id);
    int getBlogByTitle(String  title);

    List<Blog> findBlogsBySearch(BlogQuery blogQuery);

    void saveTagAndBlog(Map<String, Long> map);



    void deleteTagAndBlog(Long blogId);

    int update(Blog blog);

    List<Blog> findAll();

    List<Blog> findrecommendTop(int size);

    List<Blog> findBlogsByString(String query);

    Blog getBlogDetailById(Long id);

    void updateViews(Long id);

    List<Blog> findByTypeId(Long id);

    List<Blog> findByTagId(Long tagId);

    List<String> findGroupYear();

    List<Blog> findByYear(String year);

    Long countBlog();

    Date findLastUpdateTime();
}
