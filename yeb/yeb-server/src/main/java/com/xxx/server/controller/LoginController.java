package com.xxx.server.controller;

import com.sun.security.auth.PrincipalComparator;
import com.xxx.server.pojo.Admin;
import com.xxx.server.pojo.AdminLoginParams;
import com.xxx.server.pojo.RespBean;
import com.xxx.server.service.IAdminService;
import com.xxx.server.service.impl.AdminServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author Wu Zicong
 * @create 2021-04-24 10:23
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {
    @Autowired
    private IAdminService adminService;
    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParams adminLoginParams, HttpServletRequest request){
        return adminService.login
                (adminLoginParams.getUsername(),adminLoginParams.getPassword(),
                        adminLoginParams.getCode(),request);
    }

    @ApiOperation("获取当前登陆用户的信息")
    @GetMapping("/admin/info")
    public Admin adminInfo(Principal principal){
        String username = principal.getName();
        Admin admin = adminService.getAdminByUsername(username);
        admin.setPassword(null);//别把密码给他
        admin.setRoles(adminService.getRoles(admin.getId()));
        return admin;
    }
    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("注销成功!");
    }

}
