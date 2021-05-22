package com.xxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxx.server.utils.AdminUtils;
import com.xxx.server.config.security.component.JwtTokenUtil;
import com.xxx.server.config.security.component.MyUserDetailsService;
import com.xxx.server.mapper.AdminMapper;
import com.xxx.server.mapper.AdminRoleMapper;
import com.xxx.server.mapper.RoleMapper;
import com.xxx.server.pojo.Admin;
import com.xxx.server.pojo.AdminRole;
import com.xxx.server.pojo.RespBean;
import com.xxx.server.pojo.Role;
import com.xxx.server.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-23
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
   @Autowired
   private AdminMapper adminMapper;
   @Autowired
   private RoleMapper roleMapper;
   @Autowired
   private AdminRoleMapper adminRoleMapper;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        //登录
        String captcha = (String)request.getSession().getAttribute("captcha");
        if(StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)){
            return RespBean.error("验证码输入错误，请重新输入");
        }
        //根据username获取UserDetails
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(username);
        System.out.println(userDetails);
        if (null == userDetails ||!passwordEncoder.matches(password,userDetails.getPassword() )){
            //如果userDetails是空的或者密码匹配不上，返回错误信息
            return RespBean.error("用户名或密码不正确");
        }
        if(!userDetails.isEnabled()){
            return RespBean.error("账号被禁用，请联系管理员");
        }
        //更新security登录用户对象
        //根据UserDetails生成token
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        //把拿到token放到security的全局对象中，跳转页面获取token才能获取到
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        System.out.println("拿到了:"+SecurityContextHolder.getContext().getAuthentication());
        //System.out.println(authenticationToken);
        //生成放在前端的token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return RespBean.success("登录成功",tokenMap);
    }

    @Override
    public Admin getAdminByUsername(String username) {
        QueryWrapper<Admin> adminQueryWrapper =
                new QueryWrapper<Admin>().eq("username", username).eq("enabled", true);
        Admin admin = adminMapper
                .selectOne(adminQueryWrapper);
        if(null == admin){
            RespBean.error("无法获取用户信息");
        }
        return admin;
    }

    /**
     * 根据用户id查对应的角色列表
     * @param adminId
     * @return
     */
    @Override
    public List<Role> getRoles(Integer adminId) {

        return roleMapper.getRoles(adminId);
    }

    /**
     * 获取所有操作员
     * @param keywords
     * @return
     */
    @Override
    public List<Admin> getAllAdmins(String keywords) {
    return adminMapper.getAllAdmins(AdminUtils.getCurrentAdmin().getId(),keywords);
    }
    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    @Override
    @Transactional
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId",adminId));
        Integer result = adminRoleMapper.addAdminRole(adminId, rids);
        if(rids.length==result){ //如果受影响行数等于rids的长度，说明全部更新完成
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败！");
    }

    /**
     * 更新用户密码
     * @param oldPass
     * @param pass
     * @param adminId
     * @return
     */
    @Override
    public RespBean updateAdminPassword(String oldPass, String pass, Integer adminId) {
        Admin admin = adminMapper.selectById(adminId);
        if(admin!=null){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            //先比较旧密码是否正确
            if(passwordEncoder.matches(oldPass,admin.getPassword())){
                //更新为新密码，记得加密
                admin.setPassword(passwordEncoder.encode(pass));
                int result = adminMapper.updateById(admin);
                if(result>0){
                    return RespBean.success("更新成功");
                }
            }
        }
        return RespBean.error("更新失败");
    }

    @Override
    public RespBean updateAdminUserFace(String url, Integer id, Authentication authentication) {
        Admin admin = adminMapper.selectById(id);
        admin.setUserFace(url);
        int result = adminMapper.updateById(admin);
        if(result == 1){
//            Admin currentAdmin = AdminUtils.getCurrentAdmin();
//            currentAdmin.setUserFace(url);
            Admin principal = (Admin)authentication.getPrincipal();
            principal.setUserFace(url);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal,
                    null, authentication.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            return RespBean.success("更新成功！",url);
        }
        return RespBean.error("更新失败！");
    }
}
