package com.moduo.mapper;

import com.moduo.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-29
 */
public interface UserMapper extends BaseMapper<User> {

    User findByUsernameAndPassword(String username, String password);
}
