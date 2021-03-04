package com.lxl.controller;

import com.github.pagehelper.PageInfo;
import com.lxl.pojo.Blog;
import com.lxl.pojo.Type;
import com.lxl.service.BlogService;
import com.lxl.service.TypeService;
import com.lxl.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author : lxl
 * @create : 2021/2/27 13:05
 * @describe:
 */
@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    @GetMapping("/types")
    public String types( @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, Long id, Model model){
        //查询到按所包含博客数量的从大到小排列的type
        List<Type> types=typeService.listTypeTop(10000);

        model.addAttribute("types",types);
        if (id==-1){
            id=types.get(0).getId();
        }
        List<Blog> blogList=blogService.findBlogsByTypeId(pageNum,8,id);
        PageInfo<Blog> pageInfo =new PageInfo<>(blogList) ;
        model.addAttribute("page",pageInfo);
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
