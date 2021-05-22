package com.moduo.mapper;

import com.moduo.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-29
 */
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 根据blogId查询顶级评论
     * @param blogId
     * @return
     */
    List<Comment> getTopComments(Long blogId);

    /**
     * 查找评论的子代
     * @param parentCommentId
     * @return
     */
    List<Comment> getReplyComments(@Param("parentCommentId") Long parentCommentId);

    /**
     * 通过parentCommentId查询parentComment
     * @param parentCommentId
     * @return
     */
    List<Comment> getParentComments(@Param("parentCommentId") Long parentCommentId);
}
