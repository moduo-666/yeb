package com.moduo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author Wu Zicong
 * @create 2021-04-29 17:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_blog")
public class Blog {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String firstPicture;
    private String flag; //标签
    private Integer views;
    private Integer commentCount;  //评论数
    private boolean appreciation;  //奖赏开启
    private boolean shareStatement; //版权开启
    private boolean commentabled; //评论开启
    private String description; //描述
//    @NotBlank
    private boolean published;

    private boolean recommend; //是否推荐
    public boolean getRecommend(){
        return this.recommend;
    }
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date updateTime;

    @NotBlank
    private String type;    //分类名


//    @TableField(exist = false)
//    private Long[] tagIds; //一篇博客可以有多个标签

//    @TableField(exist = false)
    private Long userId; //一篇博客只能有一个user
    @TableField(exist = false)
    private User user;

    @TableField(exist = false)  //一篇博客可以有多条评论
    private List<Comment> comments;

    @TableField(exist = false)
    private List<Tag> tags; //blog对应的tags
}
