package com.moduo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author Wu Zicong
 * @create 2021-04-29 17:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_comment")
public class Comment {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String nickname;
    private String email;
    private String content;
    //头像
    private String avatar;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date createTime;

    private Long blogId;
    private Long parentCommentId;

    //一条评论只能对应一篇blog
    @TableField(exist = false)
    private Blog blog;
    //评论的回复
    @TableField(exist = false)
    private List<Comment> replyComments;  //评论的子集
    //作为回复的父评论
    @TableField(exist = false)
    private Comment parentComment;

    private boolean adminComment;
}
