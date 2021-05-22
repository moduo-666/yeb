package com.moduo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moduo.pojo.Comment;
import com.moduo.mapper.CommentMapper;
import com.moduo.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-29
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;
    /**
     * 通过blogId拿到顶级评论,并根据创建时间降序
     * @param blogId
     * @return
     */
    @Override
    @Transactional
    public List<Comment> listCommentByBlogId(Long blogId) {
        List<Comment> comments = commentMapper.getTopComments(blogId);
//        System.out.println(comments);
        return eachComment(comments);
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<>();
        for(Comment comment : comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }
    /**
     * root根节点，blog不为空的对象集合
     * @param comments
     */
    private void combineChildren(List<Comment> comments) {
        for(Comment comment:comments){
            //先查找评论的子代再返回到它的replyComments属性
            List<Comment> replys = commentMapper.getReplyComments(comment.getId());
            comment.setReplyComments(replys);
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1){
                List<Comment> parentComments = commentMapper.getParentComments(reply1.getParentCommentId());

                reply1.setParentComment(parentComments.get(0));
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }
    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     */
    private void recursively(Comment comment){
        tempReplys.add(comment); //顶节点添加到临时存放集合
        //先查找评论的子代再返回到它的replyComments属性
        List<Comment> replys = commentMapper.getReplyComments(comment.getId());
        comment.setReplyComments(replys);
        if(comment.getReplyComments().size()>0){
            List<Comment> replys2 = comment.getReplyComments();
            for (Comment reply : replys2){
                List<Comment> parentComment = commentMapper.getParentComments(reply.getParentCommentId());
                reply.setParentComment(parentComment.get(0));
                tempReplys.add(reply);
                //先查找评论的子代再返回到它的replyComments属性
                List<Comment> replys3 = commentMapper.getReplyComments(reply.getId());
                reply.setReplyComments(replys3);
                if(reply.getReplyComments().size()>0){
                    recursively(reply);
                }
            }
        }
    }
    /**
     * 保存comment（考虑其有无parentCommentId）
     * @param comment
     * @return
     */
    @Override
    @Transactional
    public Comment saveComment(Comment comment) {
        //拿它的parentCommentId,如果没有则为-1
        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId != -1){
            //回复操作
            //如果这个comment有parentCommentId，
            // 则通过parentCommentId拿到parentComment后，把这个parentComment给这个comment
            comment.setParentComment(commentMapper.selectById(parentCommentId));
            comment.setParentCommentId(parentCommentId);
        }else{
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        int insert = commentMapper.insert(comment);
        if(insert==0){
            throw new RuntimeException("保存失败");
        }
        return comment;
    }
}
