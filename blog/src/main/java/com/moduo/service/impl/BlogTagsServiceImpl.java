package com.moduo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moduo.mapper.BlogMapper;
import com.moduo.mapper.BlogTagsMapper;
import com.moduo.pojo.Blog;
import com.moduo.pojo.BlogTags;
import com.moduo.service.IBlogService;
import com.moduo.service.IBlogTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wu Zicong
 * @create 2021-05-02 21:19
 */
@Service
public class BlogTagsServiceImpl extends ServiceImpl<BlogTagsMapper, BlogTags> implements IBlogTagsService {
   @Autowired
    private BlogTagsMapper blogTagsMapper;

    /**
     * 更新BlogTags表
     * @param id
     * @param tagIds
     * @return
     */
    @Override
    public Integer updateBlogTags(Long id, Long[] tagIds) {
        return blogTagsMapper.updateBlogTags(id, tagIds);
    }

    @Override
    public List<BlogTags> getListByBid(Long id) {
        return blogTagsMapper.getListByBid(id);
    }

    /**
     * 根据tagId获取该tag的所有blog
     * @param tid
     * @return
     */
    @Override
    public List<Blog> getBlogsByTagId(Long tid) {
        return blogTagsMapper.getBlogsByTagId(tid);
    }


}
