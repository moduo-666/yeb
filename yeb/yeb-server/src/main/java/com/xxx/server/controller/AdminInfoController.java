package com.xxx.server.controller;

import com.xxx.server.pojo.Admin;
import com.xxx.server.pojo.RespBean;
import com.xxx.server.service.IAdminService;
import com.xxx.server.utils.FastDFSUtils;
import io.swagger.annotations.ApiOperation;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 个人中心
 * @author Wu Zicong
 * @create 2021-05-18 11:20
 */
@RestController
public class AdminInfoController {
    @Autowired
    private IAdminService adminService;
    @ApiOperation("更新当前用户信息")
    @PutMapping("/admin/info")
    public RespBean updateAdmin(@RequestBody Admin admin, Authentication authentication){
        if(adminService.updateById(admin)){
            //记得设置进全局上下文
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(admin, null, authentication.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
    @ApiOperation(value = "更新用户密码")
    @PutMapping("/admin/pass")
    public RespBean updateAdminPassword(@RequestBody Map<String,Object> info){
        String oldPass = (String) info.get("oldPass");
        String pass = (String) info.get("pass");
        Integer adminId = (Integer)info.get("adminId");
        return adminService.updateAdminPassword(oldPass,pass,adminId);
    }
    @ApiOperation(value = "更新用户头像")
    @PostMapping("/admin/userface")
    public RespBean updateAdminUserFace(MultipartFile file,Integer id,Authentication authentication){
        String[] filePath = FastDFSUtils.upload(file);
       String url =  FastDFSUtils.getTrackerUrl()+filePath[0] + "/" + filePath[1];
       return adminService.updateAdminUserFace(url,id,authentication);
    }
}
