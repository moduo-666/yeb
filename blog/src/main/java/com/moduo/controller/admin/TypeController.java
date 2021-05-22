package com.moduo.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moduo.pojo.Blog;
import com.moduo.pojo.Type;
import com.moduo.service.IBlogService;
import com.moduo.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-29
 */
@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private ITypeService typeService;
    @GetMapping("/types")
    public String type(@RequestParam(value = "pn",defaultValue = "1") Integer pn, Model model){
        //分页查询数据
        Page<Type> typePage = new Page<Type>(pn,5); //一页5个
        //分页查询的结果
        Page<Type> page = typeService.page(typePage, null);


        long current = page.getCurrent(); //当前页数
        long pages = page.getPages(); //总页数
        long total = page.getTotal(); //总记录数
        List<Type> records = page.getRecords();//真正查出的数据库的数据
        model.addAttribute("page",page);
        return "admin/types";
    }
    @GetMapping("/typeDelete/{id}")
    public String typeDelete(@PathVariable("id") Long id){
        typeService.removeById(id);
        return "redirect:/admin/types";
    }
    @GetMapping("/typesAddPage")
    public String typeAddPage(Model model){
        model.addAttribute("type",new Type());
        return "admin/type-input";
    }
    @PostMapping("/typesAdd")
    @Transactional
    public String typeAdd(@Valid Type type, BindingResult result,RedirectAttributes rd){
        Type typeByName = typeService.getTypeByName(type.getName());
        if(typeByName != null){
            result.rejectValue("name","nameError","名称已存在");

        }
        if(result.hasErrors()){
            return "admin/type-input";
        }
        boolean save = typeService.save(type);
        if(!save){
            rd.addFlashAttribute("message","添加失败");
        }else{
            rd.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/types";
    }
    @GetMapping("/typeEditPage/{id}")
    public String editTypePage(@PathVariable("id")Long id,Model model){
        model.addAttribute("type",typeService.getById(id));
        return "admin/type-edit";
    }
    @PostMapping("/typeEdit")
    @Transactional
    public String typeEdit(@Valid Type type, BindingResult result,RedirectAttributes rd){
        Type typeByName = typeService.getTypeByName(type.getName());
        System.out.println(typeByName);
        if(typeByName != null){
            result.rejectValue("name","nameError","没有做任何修改！");
        }
        if(result.hasErrors()){
            return "admin/type-edit";
        }
        boolean save = typeService.updateById(type);
        if(!save){
            rd.addFlashAttribute("message","修改失败");
        }else{
            rd.addFlashAttribute("message","修改成功");
        }
        return "redirect:/admin/types";
    }
}
