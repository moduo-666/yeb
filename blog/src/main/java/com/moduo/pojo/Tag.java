package com.moduo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Wu Zicong
 * @create 2021-04-29 17:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_tag")
public class Tag {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    @TableField(exist = false)
    private List<Blog> blogs; //一个标签也可以对应多篇博客
}
