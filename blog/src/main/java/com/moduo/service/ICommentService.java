package com.moduo.service;

import com.moduo.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-29
 */
public interface ICommentService extends IService<Comment> {
    /**
     * 通过blogId拿到顶级评论
     * @param blogId
     * @return
     */
    List<Comment> listCommentByBlogId(Long blogId);
    /**
     * 保存comment（考虑其有无parentComment）
     * @param comment
     * @return
     */
    Comment saveComment(Comment comment);

//    /**
//     * 查找评论的子代
//     * @param commentId
//     */
//    List<Comment> getReplyComments(Integer commentId);
}
