package com.moduo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moduo.pojo.Blog;
import com.moduo.pojo.Type;
import com.moduo.pojo.User;
import com.moduo.service.IBlogService;
import com.moduo.service.ITypeService;
import com.moduo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Wu Zicong
 * @create 2021-05-06 13:41
 */
@Controller
public class TypeShowController {
    @Autowired
    private ITypeService typeService;
    @Autowired
    private IBlogService blogService;
    @Autowired
    private IUserService userService;
    @GetMapping("/types/{name}")
    public String types(@PathVariable(required = false) String name,
                        @RequestParam(value = "pn",defaultValue = "1") Integer pn,
                        Model model){
        List<Type> types = typeService.list();
        //把type对应的blogs给它
        for(Type type: types){
            List<Blog> blogs = blogService.list(new QueryWrapper<Blog>().eq("type", type.getName())
                                                                        .eq("published",true));
            type.setBlogs(blogs);
        }
        if("first".equals(name)){
             name = types.get(0).getName();
        }
        //分页查询数据
        Page<Blog> blogPage = new Page<Blog>(pn,2); //一页5个
        //分页查询的结果
        Page<Blog> page = blogService.page(blogPage,new QueryWrapper<Blog>().eq("type",name)
                                                                             .eq("published",true));
        long current = page.getCurrent(); //当前页数
        long pages = page.getPages(); //总页数
        long total = page.getTotal(); //总记录数
        System.out.println(total);
        List<Blog> records = page.getRecords();//真正查出的数据库的数据
        System.out.println(records);
        getUserList(records); //把blog对应的user也给它（这里注意record和page存放的值的区别）
        model.addAttribute("types",types);
        model.addAttribute("page",page);
        model.addAttribute("records",records);
        model.addAttribute("actionType",name);
        return "types";
    }
    /**
     * 通过blogList中的userIdList拿到UserList
     * @param records
     * @return
     */
    private void getUserList(List<Blog> records){
        //通过userId拿到user
        for (Blog blog : records){
            User user = userService.getById(blog.getUserId());
            blog.setUser(user);
        }
    }
}
