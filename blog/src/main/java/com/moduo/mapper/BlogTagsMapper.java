package com.moduo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moduo.pojo.Blog;
import com.moduo.pojo.BlogTags;
import com.moduo.pojo.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Wu Zicong
 * @create 2021-05-02 21:17
 */
public interface BlogTagsMapper extends BaseMapper<BlogTags> {
    Integer updateBlogTags(@Param("bid") Long id, @Param("tids") Long[] tagIds);

    List<BlogTags> getListByBid(@Param("bid") Long id);
    /**
     * 根据tagId获取该tag的所有blog
     * @param tid
     * @return
     */
    List<Blog> getBlogsByTagId(@Param("tid") Long tid);
}
