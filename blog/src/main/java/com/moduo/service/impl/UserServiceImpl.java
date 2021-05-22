package com.moduo.service.impl;

import com.moduo.pojo.User;
import com.moduo.mapper.UserMapper;
import com.moduo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.security.DigestException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    /**
     * 根据密码和用户名检查User
     * @param username
     * @param password
     * @return
     */
    @Override
    public User checkUser(String username, String password) {
        User user = userMapper.findByUsernameAndPassword(username, password);
        if(null==user){
            return null;
        }
        return user;
    }
}
