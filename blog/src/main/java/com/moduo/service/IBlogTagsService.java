package com.moduo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moduo.pojo.Blog;
import com.moduo.pojo.BlogTags;
import com.moduo.pojo.Tag;

import java.util.List;

/**
 * @author Wu Zicong
 * @create 2021-05-02 21:18
 */
public interface IBlogTagsService extends IService<BlogTags> {
    Integer updateBlogTags(Long id, Long[] tagIds);


    List<BlogTags> getListByBid(Long id);

    /**
     * 根据tagId获取该tag的所有blog
     * @param id
     * @return
     */
    List<Blog> getBlogsByTagId(Long tid);
}
