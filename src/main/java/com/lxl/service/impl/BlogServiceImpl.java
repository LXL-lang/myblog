package com.lxl.service.impl;

import com.github.pagehelper.PageHelper;
import com.lxl.NotFoundException;
import com.lxl.mapper.BlogMapper;
import com.lxl.pojo.Blog;
import com.lxl.pojo.Tag;
import com.lxl.service.BlogService;
import com.lxl.service.TagService;
import com.lxl.util.MarkdownUtils;
import com.lxl.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private TagService tagService;
    @Override
    public Blog getBlogById(Long id) {
        return  blogMapper.getBlogById(id);
    }

    @Override
    public List<Blog> findBlogsBySearch(Integer pageNum, Integer pageSize, BlogQuery blogQuery) {
        if (blogQuery.isDraft()){
            blogQuery.setPublished(false);
        }else {
            blogQuery.setPublished(true);
        }
        PageHelper.startPage(pageNum,pageSize);
       List<Blog> blogList= blogMapper.findBlogsBySearch(blogQuery);
        return blogList;
    }
    @Transactional
    @Override
    public int saveBlog(Blog blog) throws ParseException {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);//初始浏览次数为0
            int count= blogMapper.save(blog);
            //将标签的数据存到t_blogs_tag表中
            List<Tag> tags = blog.getTags();
            Long blogId=blog.getId();
            for (Tag tag : tags) {
                Map<String,Long> map=new HashMap<>();
                map.put("blogId",blogId);
                map.put("tagId",tag.getId());
                blogMapper.saveTagAndBlog(map);
            }
        return count;
    }
    @Transactional
    @Override
    public int updateBlog(Long id, Blog blog) throws ParseException {
        Blog b=blogMapper.getBlogById(id);
        if (b==null){
            throw new NotFoundException("该博客不存在");
        }

        blog.setUpdateTime(new Date());
        int count=blogMapper.update(blog);
        //将t_blogs_tag表原来的删除
        blogMapper.deleteTagAndBlog(blog.getId());
        //将标签的数据添加到t_blogs_tag表中
        List<Tag> tags = blog.getTags();
        Long blogId=blog.getId();
        for (Tag tag : tags) {
            Map<String,Long> map=new HashMap<>();
            map.put("blogId",blogId);
            map.put("tagId",tag.getId());
            blogMapper.saveTagAndBlog(map);
        }
        return count;
    }
    @Transactional
    @Override
    public int deleteBlog(Long id) {
        blogMapper.deleteTagAndBlog(id);
       return blogMapper.deleteById(id);
    }

    @Override
    public List<Blog> listBlogByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
       List<Blog> blogList= blogMapper.findAll();
        for (Blog blog : blogList) {
            blog.setTags(tagService.listTag(blog.getTagIds()));
        }
        return blogList;
    }
    //前几条博客
    @Override
    public List<Blog> listBlogrecommendTop(int size) {
        return blogMapper.findrecommendTop(size);

    }
    //通过String参数匹配博客的内容或标题，进行查询
    @Override
    public List<Blog> findListBlogByString(int pageNum, int pageSize,String query) {
        PageHelper.startPage( pageNum, pageSize);
        List<Blog> blogList = blogMapper.findBlogsByString(query);
        return blogList;

    }
  @Transactional
    @Override
    public Blog getBlogAndConvert(Long id) {
        //浏览次数加一
        blogMapper.updateViews(id);
        Blog blog = blogMapper.getBlogDetailById(id);
        if (blog==null){
            throw new NotFoundException("该博客不存在");
        }
        //查询设置标签
        blog.setTags(tagService.listTag(blog.getTagIds()));
        //将文本内容进行转化
        Blog b=new Blog();
        //设置复制对象的属性，不至于直接操作数据库中的数据
        BeanUtils.copyProperties(blog,b);
        String content=b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }
        //通过typeid查询出blogs
    @Override
    public List<Blog> findBlogsByTypeId(Integer pageNum, int pageSize, Long id) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogList= blogMapper.findByTypeId(id);
        for (Blog blog : blogList) {
            blog.setTags(tagService.listTag(blog.getTagIds()));
        }
        return blogList;
    }

    @Override
    public List<Blog> findBlogsByTagId(Integer pageNum,int pageSize, Long id) {
        PageHelper.startPage(pageNum,pageSize);
       List<Blog> blogList= blogMapper.findByTagId(id);

        for (Blog blog : blogList) {
            blog.setTags(tagService.listTag(blog.getTagIds()));
        }
        return blogList;
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        //所有年份分组，查询出所有的年份
        List<String>  years=blogMapper.findGroupYear();
        /*为了保证归档年份按顺序显示，使用LinkedHashMap存放*/
        //Map<String, List<Blog>> map = new HashMap<>();
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        for (String year : years) {
            map.put(year,blogMapper.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {

        return  blogMapper.countBlog();
    }

    /**
     * 最后更新时间
     * @return
     */
    @Override
    public Date findLastUpdateTime() {
        return blogMapper.findLastUpdateTime();
    }

}
