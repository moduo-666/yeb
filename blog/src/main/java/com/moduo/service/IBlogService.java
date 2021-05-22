package com.moduo.service;

import com.moduo.pojo.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moduo.pojo.Tag;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-29
 */
public interface IBlogService extends IService<Blog> {
    List<Blog> getListByTypeId(Integer typeId);

    /**
     * 根据blogId拿到它的tags
     * @param id
     * @return
     */
    List<Tag> getTagsByBlogId(Long id);

    Map<String, List<Blog>> archiveBlog();
}
