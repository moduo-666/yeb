package com.xxx.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.server.pojo.MenuRole;
import com.xxx.server.pojo.RespBean;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-23
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    RespBean updateMenuRole(Integer rid, Integer[] mIds);

    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     *
     */
    Integer insertRecord(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
