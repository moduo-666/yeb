package com.xxx.server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用户登录实体类
 * @author Wu Zicong
 * @create 2021-04-24 10:14
 */
@Data
@EqualsAndHashCode(callSuper = false) //是否实现equals方法和hashcode方法
@Accessors(chain = true)  //chain = true 表示set方法返回当前对象
@ApiModel(value = "AdminLogin对象",description = "")
public class AdminLoginParams {
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @ApiModelProperty(value = "验证码",required = true)
    private String code;
}
