package com.lxl.controller.admin;

import com.github.pagehelper.PageInfo;
import com.lxl.pojo.Type;
import com.lxl.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;
    //类型列表(所有的类型)
    @GetMapping("/types")
    public String types(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, Model model){
        List<Type> typeList = typeService.getAllTypeByPage(pageNum, 8);
        PageInfo<Type> pageInfo =new PageInfo<>(typeList) ;
        model.addAttribute("pageInfo",pageInfo);
        return "admin/types";
    }
    //返回新增页面
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute(new Type());
        return "admin/types-input";
    }
    @PostMapping("/types/add")
    public String add(Type type,  RedirectAttributes attributes){
        //校验该类型是否已经存在
        if (typeService.getTypeByName(type.getName())!=null){
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        }
        int count = typeService.saveType(type);

        if (count<=0){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","恭喜，新增成功！");
        }
        return "redirect:/admin/types";
    }
    @GetMapping("/types/{id}/input")
    //跳转到要修改页面
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getTypeById(id));
        return "admin/types-input";
    }
    @PostMapping("/types/edit/{id}")
    public String edit(Type type,@PathVariable Long id,  RedirectAttributes attributes){
        //校验该类型是否已经存在
        if (typeService.getTypeByName(type.getName())!=null){
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        }
        int count = typeService.updateType(id,type);

        if (count<=0){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功！");
        }
        return "redirect:/admin/types";
    }
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        int count = typeService.delete(id);
        if (count<=0){
            attributes.addFlashAttribute("message","删除失败");
        }else {
            attributes.addFlashAttribute("message","删除成功！");
        }
        return "redirect:/admin/types";
    }

}
