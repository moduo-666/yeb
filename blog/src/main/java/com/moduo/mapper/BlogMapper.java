package com.moduo.mapper;

import com.moduo.pojo.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moduo.pojo.Tag;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-29
 */
public interface BlogMapper extends BaseMapper<Blog> {

    List<Blog> getListByTypeId(Integer typeId);
    /**
     * 根据blogId拿到它的tags
     * @param id
     * @return
     */
    List<Tag> getTagsByBlogId(Long id);

    /**
     * 拿到全部年份
     * @return
     */
    List<String> findGroupYear();

    /**
     * 根据年份查询blogs
     * @param year
     * @return
     */
    List<Blog> findBlogsByYear(String year);
}
