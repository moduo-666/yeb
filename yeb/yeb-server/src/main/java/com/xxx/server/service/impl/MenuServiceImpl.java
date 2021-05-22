package com.xxx.server.service.impl;


import com.xxx.server.utils.AdminUtils;
import com.xxx.server.mapper.MenuMapper;
import com.xxx.server.pojo.Menu;
import com.xxx.server.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-23
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    /**
     * 通过用户id查询菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenuByAdminId() {
        Integer adminId = AdminUtils.getCurrentAdmin().getId();
        //System.out.println(adminId);
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
       //从redis获取菜单数据
        List<Menu> menus = (List<Menu>)valueOperations.get("menu_" + adminId);
        if(CollectionUtils.isEmpty(menus)){
            //如果为空去数据库获取
            menus = menuMapper.getMenuByAdminId(adminId);
            //将数据设置到redis中
            valueOperations.set("menu_"+adminId,menus);
        }

        return menus;
    }

    /**
     * 根据角色获取菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }
    /**
     * 查询所有菜单
     * @return
     */
    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();

    }
}
