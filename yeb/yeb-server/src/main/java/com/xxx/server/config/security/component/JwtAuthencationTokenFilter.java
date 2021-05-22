package com.xxx.server.config.security.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 思考：BasicAuthenticationFilter与 OncePerRequestFilter的区别，
 * 怎么替换为BasicAuthenticationFilter
 * @author Wu Zicong
 * @create 2021-04-24 11:40
 */
@Component
public class JwtAuthencationTokenFilter extends OncePerRequestFilter  {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private MyUserDetailsService userDetailsService;



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader(tokenHeader);
        //存在token
        if(null != authHeader && authHeader.startsWith(tokenHead)){

            String authToken = authHeader.substring(tokenHead.length());
            //System.out.println(authToken);
            //从token中拿到username
            String usernameFromToken = jwtTokenUtil.getUsernameFromToken(authToken);
           // System.out.println(usernameFromToken);
          //  UserDetails userDetails = jwtTokenUtil.parseAccessToken(authToken);
            //在security全局上下文中拿用户对象
           // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(null!=usernameFromToken){
                //先登录
                UserDetails userDetails = userDetailsService.loadUserByUsername(usernameFromToken);
                //判断token是否有效
                if(jwtTokenUtil.validateToken(authToken,userDetails)) {
                    //这里为什么每次都要重新设置UsernamePasswordAuthenticationToken并把
                    UsernamePasswordAuthenticationToken authenticationToken = new
                            UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    //把token重新设置到用户对象里
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }else{
                    throw new UsernameNotFoundException("token无效");
                }
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
