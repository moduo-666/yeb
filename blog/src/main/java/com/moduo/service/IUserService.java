package com.moduo.service;

import com.moduo.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-29
 */
public interface IUserService extends IService<User> {
    /**
     * 根据密码和用户名检查User
     * @param username
     * @param password
     * @return
     */
    User checkUser(String username,String password);

}
