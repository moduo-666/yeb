package com.xxx.server.config.security.component;

import com.xxx.server.mapper.AdminMapper;
import com.xxx.server.pojo.Admin;
import com.xxx.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 重写UserDetailsService
 * @author Wu Zicong
 * @create 2021-04-24 11:23
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private IAdminService adminService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.getAdminByUsername(username);
        if(null==admin){
            throw new UsernameNotFoundException("用户名或密码不正确");
        }
        admin.setRoles(adminService.getRoles(admin.getId()));
        return admin;
    }
}
