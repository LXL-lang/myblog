package com.lxl.service;

import com.lxl.pojo.Blog;
import com.lxl.vo.BlogQuery;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlogById(Long id);
    //分页查询,有条件查询
    List<Blog> findBlogsBySearch(Integer pageNum, Integer pageSize, BlogQuery blogQuery);
    //新增
    int saveBlog(Blog blog) throws ParseException;
    int updateBlog(Long id,Blog blog) throws ParseException;
    int deleteBlog(Long id);
    //查询所有的博客，分页
    List<Blog> listBlogByPage(int pageNum,int pageSize);
    //查询前几的博客，按更新时间排序
    List<Blog> listBlogrecommendTop(int size);

    List<Blog> findListBlogByString(int pageNum, int pageSize,String query);
    Blog getBlogAndConvert(Long id);
    List<Blog> findBlogsByTypeId(Integer pageNum, int pageSize, Long id);
    List<Blog> findBlogsByTagId(Integer pageNum, int pageSize,Long id);
    //归档
    Map<String,List<Blog>> archiveBlog();
    Long countBlog();

    Date findLastUpdateTime();
}
