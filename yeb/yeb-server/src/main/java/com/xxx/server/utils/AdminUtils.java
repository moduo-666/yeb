package com.xxx.server.utils;

import com.xxx.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 操作员工具类
 * @author Wu Zicong
 * @create 2021-05-08 19:16
 */
public class AdminUtils {
    /**
     * 获取当前操作登录员
     * @return
     */
    public static Admin getCurrentAdmin(){

       return (Admin)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
