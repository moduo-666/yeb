package com.moduo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Wu Zicong
 * @create 2021-04-29 17:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_type")
public class Type {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @NotBlank(message="分类名称不能为空")
    private String name;

    //一个分类有很多篇博客
    @TableField(exist = false)
    private List<Blog> blogs;
}
