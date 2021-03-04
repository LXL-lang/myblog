package com.lxl.controller.admin;

import com.github.pagehelper.PageInfo;
import com.lxl.pojo.Blog;
import com.lxl.pojo.User;
import com.lxl.service.BlogService;
import com.lxl.service.TagService;
import com.lxl.service.TypeService;
import com.lxl.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {
    private static final String INPUT="admin/blogs-input";
    private static final String LIST="admin/blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @GetMapping("/blogs")
    public String blogs(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, BlogQuery blogQuery, Model model){
        model.addAttribute("types",typeService.findAll());
        List<Blog> blogList=blogService.findBlogsBySearch(pageNum,5,blogQuery);
        PageInfo<Blog> pageInfo =new PageInfo<>(blogList) ;
        model.addAttribute("pageInfo",pageInfo);
        return LIST;
    }
    @PostMapping("/blogs/search")
    public String search(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, BlogQuery blogQuery, Model model){
        List<Blog> blogList=blogService.findBlogsBySearch(pageNum,5,blogQuery);
        PageInfo<Blog> pageInfo =new PageInfo<>(blogList) ;
        model.addAttribute("pageInfo",pageInfo);
        return "admin/blogs ::blogList"; //返回admin/blogs页面下的一个片段blogList,实现局部刷新
    }
    @RequestMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog",new Blog());
        return INPUT;
    }
    //查询所有的type还有tag
    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.findAll());
        model.addAttribute("tags",tagService.findAll());
    }
    @RequestMapping("/blogs/{id}/input")
    public String edit(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog = blogService.getBlogById(id);
        blog.setType(typeService.getTypeById(blog.getTypeId()));
        model.addAttribute("blog",blog );
        return INPUT;
    }

    @PostMapping("blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes) throws ParseException {

        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        int count=0;
        if (blog.getId()==null){
            count=blogService.saveBlog(blog);
        }else{
          count= blogService.updateBlog(blog.getId(),blog);
        }
        if (count<=0){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","恭喜，操作成功！");
        }
        return REDIRECT_LIST;
    }
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        int count = blogService.deleteBlog(id);
        if (count<=0){
            attributes.addFlashAttribute("message","删除失败");
        }else {
            attributes.addFlashAttribute("message","删除成功！");
        }
        return REDIRECT_LIST;
    }
}
