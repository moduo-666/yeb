package com.moduo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moduo.pojo.Blog;
import com.moduo.pojo.Tag;
import com.moduo.pojo.User;
import com.moduo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Wu Zicong
 * @create 2021-05-06 13:41
 */
@Controller
public class TagShowController {
    @Autowired
    private IBlogTagsService blogTagsService;
    @Autowired
    private ITagService tagService;
    @Autowired
    private IBlogService blogService;
    @Autowired
    private IUserService userService;
    @GetMapping("/tags/{name}")
    public String types(@PathVariable(required = false) String name,
                        @RequestParam(value = "pn",defaultValue = "1") Integer pn,
                        Model model){

        List<Tag> tags = tagService.list();
        if("first".equals(name)){
            name = tags.get(0).getName();
        }
        List<Blog> blogList = null;
        //把tags对应的blogs给它
        for(Tag tag: tags){
            List<Blog> blogs = blogTagsService.getBlogsByTagId(tag.getId());
            tag.setBlogs(blogs);
            if(name.equals(tag.getName())){
                blogList = tag.getBlogs();
            }
        }
        getUserList(blogList);//把blog对应的user也给它
        Tag tag = tagService.getOne(new QueryWrapper<Tag>().eq("name", name));
        //这块就不分页了
        model.addAttribute("tags",tags);
        model.addAttribute("blogList",blogList);
        model.addAttribute("actionTag",name);
        return "tags";
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
