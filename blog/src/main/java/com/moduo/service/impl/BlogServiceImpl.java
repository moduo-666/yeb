package com.moduo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moduo.pojo.Blog;
import com.moduo.mapper.BlogMapper;
import com.moduo.pojo.Tag;
import com.moduo.service.IBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-29
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<Blog> getListByTypeId(Integer typeId) {
        return blogMapper.getListByTypeId(typeId);
    }
    /**
     * 根据blogId拿到它的tags
     * @param id
     * @return
     */
    @Override
    public List<Tag> getTagsByBlogId(Long id) {
        return blogMapper.getTagsByBlogId(id);
    }

    /**
     * 拿到归档信息
     * @return
     */
    @Override
    public Map<String, List<Blog> > archiveBlog() {
       List<String> years =  blogMapper.findGroupYear();
        System.out.println(years);
       Map<String,List<Blog>> map = new LinkedHashMap<>();
       for(String year : years){
           map.put(year, blogMapper.findBlogsByYear(year));
       }
        System.out.println(map);
       return map;
    }
}
