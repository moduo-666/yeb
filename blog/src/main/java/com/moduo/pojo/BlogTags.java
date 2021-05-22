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
@TableName("t_blogtags")
public class BlogTags {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long bid;
    private Long tid;
}
