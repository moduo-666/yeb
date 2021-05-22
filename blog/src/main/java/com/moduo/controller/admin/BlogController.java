package com.moduo.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moduo.pojo.*;
import com.moduo.service.IBlogService;
import com.moduo.service.IBlogTagsService;
import com.moduo.service.ITagService;
import com.moduo.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

/**
 * mybatisplus插入后自动把id映射到实体的，直接拿你前端传来数据的实体getid就可以用了
 * @author Wu Zicong
 * @create 2021-04-30 19:18
 */
@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    private IBlogService blogService;
    @Autowired
    private ITypeService typeService;
    @Autowired
    private ITagService tagService;
    @Autowired
    private IBlogTagsService blogTagsService;
    @GetMapping("/blogs")
    public String blogs(@RequestParam(value = "pn",defaultValue = "1") Integer pn, Model model){
        //分页查询数据
        Page<Blog> blogPage = new Page<Blog>(pn,5); //一页5个
        //分页查询的结果
        Page<Blog> page = blogService.page(blogPage,null);
        //给个分类列表
        List<Type> typeList = typeService.list();
        long current = page.getCurrent(); //当前页数
        long pages = page.getPages(); //总页数
        long total = page.getTotal(); //总记录数
        List<Blog> records = page.getRecords();//真正查出的数据库的数据
        model.addAttribute("page",page);
        model.addAttribute("typeList",typeList);
        return "admin/blogs";
    }
    @PostMapping("/blogSelect")
    public String blogSelect(@RequestParam(value = "pn",defaultValue = "1") Integer pn, Model model, Blog blog){
        //分页查询数据
        Page<Blog> blogPage = new Page<Blog>(pn,100); //一页5个 ,默认第一页//还没实现分类，先写100个吧
        //分页查询的结果
        Page<Blog> page = null;
        QueryWrapper<Blog> eq = null;
        //如果全为空则重定向回全部blog展示
        if("".equals(blog.getTitle())&& "".equals(blog.getType()) && blog.getRecommend()== false){
            return "redirect:/admin/blogs";

        }
        //有推荐的先来
        if(blog.getRecommend()== true){
            //如果标题不为空而类型为空
            if(!"".equals(blog.getTitle()) && "".equals(blog.getType())){
               eq = new QueryWrapper<Blog>()
                       .like("title",blog.getTitle())
                        .eq("recommend", true);
                //如果标题为空而类型不为空
            }else if("".equals(blog.getTitle()) && !"".equals(blog.getType())){
                eq = new QueryWrapper<Blog>()
                        .eq("type",blog.getType())
                        .eq("recommend", true);
                //如果都有
            }else if(!"".equals(blog.getTitle()) && !"".equals(blog.getType())){
                eq = new QueryWrapper<Blog>()
                        .eq("type",blog.getType())
                        .like("title",blog.getTitle())
                        .eq("recommend", true);
                //剩下的就是都没有了(只标了推荐)
            }else{
                eq = new QueryWrapper<Blog>()
                        .eq("recommend", true);
            }
        }else{  //没推荐的
            //如果标题不为空而类型为空
            if(!"".equals(blog.getTitle()) && "".equals(blog.getType())){
                eq = new QueryWrapper<Blog>()
                        .like("title",blog.getTitle())
                        .eq("recommend", false);
                //如果标题为空而类型不为空
            }else if("".equals(blog.getTitle()) && !"".equals(blog.getType())){
                eq = new QueryWrapper<Blog>()
                        .eq("type",blog.getType())
                        .eq("recommend", false);
                //如果都有
            }else if(!"".equals(blog.getTitle()) && !"".equals(blog.getType())){
                eq = new QueryWrapper<Blog>()
                        .eq("type",blog.getType())
                        .like("title",blog.getTitle())
                        .eq("recommend", false);
                //剩下的就是都没有了(推荐都没标) //这一步是永远走不到了(bug)
            }else{
                eq = new QueryWrapper<Blog>()
                        .eq("recommend", false);
            }
        }
            if(null != eq){ //找到了
                page = blogService.page(blogPage,eq);
            }else{   //找不到，就是没有咯
                page = blogService.page(blogPage,null);
            }
        //给个分类列表
        List<Type> typeList = typeService.list();
        long current = page.getCurrent(); //当前页数
        long pages = page.getPages(); //总页数
        long total = page.getTotal(); //总记录数
        List<Blog> records = page.getRecords();//真正查出的数据库的数据
        model.addAttribute("page",page);
        model.addAttribute("typeList",typeList);
        //如果拿到blog，说明上下页功能是搜索结果页的
        model.addAttribute("blog",blog);
        return "admin/blogs";
    }
    private void setTypeAndTag(Model model){
//        //给个分类列表
//        List<Type> typeList = typeService.list();
//        //给个标签列表
//        List<Tag> tagList = tagService.list();
        model.addAttribute("typeList",typeService.list());
        model.addAttribute("tagList",tagService.list());

    }
    @GetMapping("/blogAddPage")
    public String blogAddPage(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }
    private String getTids(Long id){
        List<BlogTags> list = blogTagsService.getListByBid(id);
        if(list.size() != 0) {
            StringBuffer stringBuffer = new StringBuffer();
            Iterator<BlogTags> iter = list.iterator();
            while (iter.hasNext()) {
                stringBuffer.append(iter.next().getTid() + ",");
            }
            return stringBuffer.deleteCharAt(stringBuffer.length() - 1).toString();
        }
        return null;
    }
    @GetMapping("/blogEditPage/{id}")
    public String blogEditPage(@PathVariable Long id, Model model){
        String tids = getTids(id);
        setTypeAndTag(model);
        model.addAttribute("tids",tids);
        model.addAttribute("blog",blogService.getById(id));
        return "admin/blogs-input";
    }
    @PostMapping("/blogAddAndEdit")
    @Transactional
    public String blogAddAndEdit(Long[] tagIds,@Valid Blog blog, RedirectAttributes attributes, HttpSession session){
        //谁创建的也要给他
        blog.setUserId(((User) session.getAttribute("user")).getId() );
        boolean input = false;
        if(blog.getId() == null){ //如果没有获取到id说明是插入
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0); //浏览次数
            input = blogService.save(blog);  //save成功的话会把主键id回传给实体对象
            //更新blogtags表，把要添加的bid以及它对应的tid传进去
            if(tagIds.length>0){
                blogTagsService.updateBlogTags(blog.getId(), tagIds);
            }
        }else if(blog.getId() != null){ //有id则为更新
            blog.setUpdateTime(new Date());
            input = blogService.updateById(blog);
            //更新blogtags表
            if(tagIds.length == 0){  //如果没传入tags则把原有的都删了
                blogTagsService.remove(new QueryWrapper<BlogTags>().eq("bid",blog.getId()));
            }
            if(tagIds.length>0){
                //修改的话要先删除原有tags再插入新的
                blogTagsService.remove(new QueryWrapper<BlogTags>().eq("bid",blog.getId()));
                blogTagsService.updateBlogTags(blog.getId(), tagIds);
            }
        }

        if(input){
            attributes.addFlashAttribute("message","操作成功");
        }else{
            attributes.addFlashAttribute("message","操作失败");
            throw new RuntimeException("又来");
        }
        return "redirect:/admin/blogs";
    }
    @GetMapping("/blogDelete/{id}")
    @Transactional
    public String blogDelete(@PathVariable("id") Long id){
        //要把中间表的字段也删掉
        blogTagsService.remove(new QueryWrapper<BlogTags>().eq("bid",id));
        blogService.removeById(id);
        return "redirect:/admin/blogs";
    }
}
