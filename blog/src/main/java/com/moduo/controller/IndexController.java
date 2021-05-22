package com.moduo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moduo.pojo.Blog;
import com.moduo.pojo.Tag;
import com.moduo.pojo.Type;
import com.moduo.pojo.User;
import com.moduo.service.IBlogService;
import com.moduo.service.ITypeService;
import com.moduo.service.IUserService;
import com.moduo.util.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * @author Wu Zicong
 * @create 2021-04-28 22:28
 */
@Controller
public class IndexController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IBlogService blogService;
    @Autowired
    private ITypeService typeService;

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

    /**
     * 拿到typeMap，用于展示当前页type的种类与数量
     * @param records
     * @return
     */
    private Map<String,Integer> getTypeMap(List<Blog> records){
        Map<String,Integer> typeMap = new HashMap<String,Integer>(); //存放type的map
        for(Blog blog : records){
            //这里怎么避免频繁的插入查询呢？
            //拿typeTog
            if(null == typeMap.get(blog.getType())) {
                typeMap.put(blog.getType(),1);
            }else{
                Integer nums = typeMap.get(blog.getType()) + 1;
                typeMap.put(blog.getType(),nums);
            }
        }
        return typeMap;
    }

    /**
     * 拿到tagMap，用于展示当前页tag的种类与数量
     * @param records
     * @return
     */
    private Map<String,Integer> getTagMap(List<Blog> records){
        Map<String,Integer> tagMap = new HashMap<String,Integer>(); //存放type的map
        for(Blog blog : records){
            //根据blogId拿TagList，再把tag存到tagMap中
            List<Tag> tagList =  blogService.getTagsByBlogId(blog.getId());
            for(Tag tag : tagList){
                if(null == tagMap.get( tag.getName() ) ) {
                    tagMap.put(tag.getName(),1);
                }else{
                    Integer nums = tagMap.get(tag.getName()) + 1;
                    tagMap.put(tag.getName(),nums);
                }
            }
        }
        return tagMap;
    }

    /**
     * 拿到最新的推荐博客
     * @return
     */
    private List<Blog> getRecommendBlog(){
        List<Blog> blogs = null;
        blogs = blogService.list(new QueryWrapper<Blog>()
                .eq("recommend", 1)
                .eq("published",true)
                .orderByDesc("createTime")
        );
        return blogs;
    }
    @GetMapping("/")
    public String index(@RequestParam(value = "pn",defaultValue = "1") Integer pn, Model model) {
        //分页查询数据
        Page<Blog> blogPage = new Page<Blog>(pn, 5); //一页5个
        //分页查询的结果
        Page<Blog> page = blogService.page(blogPage, new QueryWrapper<Blog>().eq("published", true));
        long current = page.getCurrent(); //当前页数
        long pages = page.getPages(); //总页数
        long total = page.getTotal(); //总记录数
        List<Blog> records = page.getRecords();//真正查出的数据库的数据
        getUserList(records); //把blog对应的user也给它（这里注意record和page存放的值的区别）
        List<Blog> recommendBlogs = getRecommendBlog();
        if (recommendBlogs.size() > 8) {
            recommendBlogs = recommendBlogs.subList(0, 8);}
            model.addAttribute("tagMap", getTagMap(records));
            model.addAttribute("page", page);
            model.addAttribute("records", records);
            model.addAttribute("typeMap", getTypeMap(records));
            model.addAttribute("recommendBlog", recommendBlogs);        //拿到有推荐的博客
            return "index";
    }
    /**
     * 把List转为分页page(有bug)
     * @param list
     * @param pn
     * @return
     */
    private Page<Blog> getPage(List<Blog> list,Integer pn){

        // 分页代码片段
        // list是所要处理的列表数据
        Page<Blog> page = new Page<Blog>(pn,5);
        // 当前页第一条数据在List中的位置
        int start = (int)((page.getCurrent() - 1) * page.getSize());
        // 当前页最后一条数据在List中的位置
        int end = (int)((start + page.getSize()) > list.size() ? list.size() : (page.getSize() * page.getCurrent()));
        page.setRecords(new ArrayList<>());
        if (page.getSize()*(page.getCurrent()-1) <= page.getTotal()) {
            if(start==end){
                List<Blog> blogList = new ArrayList<>();
                blogList.add(list.get(list.size()-1));
                page.setRecords(blogList);
            }else {
                // 分隔列表 当前页存在数据时 设置
                page.setRecords(list.subList(start, end));
            }
        }
        page.setTotal(list.size());
        return page;
    }
    @PostMapping("/search")
    public String search(@RequestParam(value = "pn",defaultValue = "1") Integer pn, Model model,
                         String query){
        Page<Blog> blogPage = new Page<Blog>(pn,5);
        //分页查询的结果
        Page<Blog> page = blogService.page(blogPage,
                new QueryWrapper<Blog>()
                        .like("title", query)
                        .eq("published",true));
        long current = page.getCurrent(); //当前页数
        long pages = page.getPages(); //总页数
        long total = page.getTotal(); //总记录数
        List<Blog> records = page.getRecords();//真正查出的数据库的数据
        getUserList(records); //把blog对应的user也给它（这里注意record和page存放的值的区别）
        model.addAttribute("records",records);
        model.addAttribute("page",page);
        model.addAttribute("query",query);
        return "search";
    }

    /**
     * 跳转到blog详情页
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}")
    @Transactional
    public String blog(@PathVariable Long id,Model model){
        Blog blog = blogService.getById(id);
        User user = userService.getById(blog.getUserId());
        List<Tag> tags = blogService.getTagsByBlogId(id);
        //把content解析转化为html格式的字符串
        String content = MarkdownUtils.markdownToHtmlExtensions(blog.getContent());
        blog.setUser(user);
        blog.setContent(content);

        blog.setViews(blog.getViews()+1);
        boolean view = blogService.updateById(blog);
        if(!view){
            throw new RuntimeException("浏览次数异常");
        }
        model.addAttribute("blog",blog);
        model.addAttribute("tags",tags);
        System.out.println("-----blog-----");
        return "blog";
    }
    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        List<Blog> recommendBlogs = getRecommendBlog();
        if (recommendBlogs.size() > 3) {
            recommendBlogs = recommendBlogs.subList(0, 3);
        }
        model.addAttribute("newblogs",recommendBlogs);
        return "_fragments :: newBlogList";
    }
}
