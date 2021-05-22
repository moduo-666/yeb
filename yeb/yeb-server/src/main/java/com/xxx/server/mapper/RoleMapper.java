package com.xxx.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-23
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户id查对应的角色列表
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);
}
