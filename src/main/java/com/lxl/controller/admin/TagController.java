package com.lxl.controller.admin;

import com.github.pagehelper.PageInfo;
import com.lxl.pojo.Tag;

import com.lxl.pojo.Type;
import com.lxl.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;
    @RequestMapping("/tags")
    public String tags(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, Model model){
        List<Tag> tagList = tagService.getAllTagByPage(pageNum, 10);
        PageInfo<Tag> pageInfo =new PageInfo<>(tagList) ;
        model.addAttribute("pageInfo",pageInfo);
        return "admin/tags";
    }
    @RequestMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }
    @PostMapping("/tags/add")
    public String add(Tag tag, RedirectAttributes attributes){
        //校验该类型是否已经存在
        if (tagService.getTagByName(tag.getName())!=null){
            attributes.addFlashAttribute("message", "不能添加重复的标签");
            return "redirect:/admin/tags/input";
        }
        int count = tagService.saveTag(tag);

        if (count<=0){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","恭喜，新增成功！");
        }
        return "redirect:/admin/tags";
    }
    @GetMapping("/tags/{id}/input")
    //跳转到要修改页面
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTagById(id));
        return "admin/tags-input";
    }
    @PostMapping("/tags/edit/{id}")
    public String edit(Tag tag,@PathVariable Long id,  RedirectAttributes attributes){
        //校验该类型是否已经存在
        if (tagService.getTagByName(tag.getName())!=null){
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/tags/input";
        }
        int count = tagService.updateTag(id,tag);

        if (count<=0){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功！");
        }
        return "redirect:/admin/tags";
    }


    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        int count = tagService.delete(id);
        if (count<=0){
            attributes.addFlashAttribute("message","删除失败");
        }else {
            attributes.addFlashAttribute("message","删除成功！");
        }
        return "redirect:/admin/tags";
    }
}
