package com.lxl.controller;

import com.github.pagehelper.PageInfo;
import com.lxl.NotFoundException;
import com.lxl.mapper.CommentMapper;
import com.lxl.pojo.Blog;
import com.lxl.pojo.Type;
import com.lxl.service.*;
import com.lxl.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private VisiteService visiteService;
    @Autowired
    private CommentService commentService;
    @RequestMapping("/")
        public String index(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, Model model){
        List<Blog> blogList = blogService.listBlogByPage(pageNum, 8);
        visiteService.increase();
        PageInfo<Blog> page =new PageInfo<>(blogList) ;
        model.addAttribute("page",page);
//       List<Type> list= typeService.listTypeTop(6);
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listBlogrecommendTop(8));
        return "index";
    }
    //全局搜索
    @RequestMapping("/search")
    public String search(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,@RequestParam String query, Model model){
        //通过String参数匹配博客的内容或标题，进行查询
        List<Blog> listBolg=blogService.findListBlogByString(pageNum, 8,"%"+query+"%");
        PageInfo<Blog> page = new PageInfo<>(listBolg);
        model.addAttribute("page",page);
        model.addAttribute("query",query);
        return "search";
    }
    @RequestMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        //根据id查询博客详情，具体
        model.addAttribute("blog",blogService.getBlogAndConvert(id));
        return "blog";
    }
    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        model.addAttribute("newblogs",blogService.listBlogrecommendTop(3));
        return "_fragments :: newblogList";
    }

    /**
     * 页面底部关于网站的信息，访客数量等
     * @param model
     * @return
     */
    @GetMapping("/footer/visite")
    public String visiteMessage(Model model){
        model.addAttribute("allvisite",visiteService.findAllVisite());
        model.addAttribute("endupdatetime",blogService.findLastUpdateTime());
        model.addAttribute("allcomment",commentService.findAll());
        return "_fragments :: visitemessage";
    }
}
