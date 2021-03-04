package com.lxl.controller;

import com.github.pagehelper.PageInfo;
import com.lxl.pojo.Blog;
import com.lxl.pojo.Tag;
import com.lxl.service.BlogService;
import com.lxl.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author : lxl
 * @create : 2021/2/27 13:05
 * @describe:
 */
@Controller
public class TagShowController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;
    @GetMapping("/tags")
    public String Tags( @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, Long id, Model model){
        //查询到按所包含博客数量的从大到小排列的Tag
        List<Tag> tags=tagService.listTagTop(10000);

        model.addAttribute("tags",tags);
        if (id==-1){
            id=tags.get(0).getId();
        }
        List<Blog> blogList=blogService.findBlogsByTagId(pageNum,8,id);
        PageInfo<Blog> pageInfo =new PageInfo<>(blogList) ;
        model.addAttribute("page",pageInfo);
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
