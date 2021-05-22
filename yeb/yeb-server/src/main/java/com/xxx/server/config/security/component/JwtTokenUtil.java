package com.xxx.server.config.security.component;

import com.xxx.server.pojo.Admin;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * JwtToken工具类
 * 根据用户名生成token ：generateToken
 * 从token中拿用户名： getUsernameFromToken
 * 判断token是否有效 ： validateToken
 * 判断token是否可以被刷新 ：canRefresh
 * 刷新token: refreshToken
 *
 * @author Wu Zicong
 * @create 2021-04-23 9:44
 */
@Component
public class JwtTokenUtil {
    //准备荷载头里的用户名和创建时间常量和用户角色
    private static final String CLAIM_KEY_USERNAME="sub";
    private static final String CLAIM_KEY_CREATED="created";
    private static final String CLAIM_KEY_ROLES="roles";
    private static final String CLAIM_KEY_ADMIN="admin";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    /**
     * 根据用户信息生成token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails){
//       先准备荷载
        Map<String,Object> claims = new HashMap<>();
        //把用户名和创建时间传进去
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }
    /**
     *根据token拿到用户信息(有待完善)
     * @param token Token信息
     * @return
     */
    public Admin parseAccessToken(String token) {
        Admin admin = null;
        if (StringUtils.isNotEmpty(token)) {
            try {
                // 解析Token
                Claims claims = getClaimsFromToken(token);
                admin = (Admin) claims.get(CLAIM_KEY_ADMIN);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return admin;
    }


    /**
     * JWT : 三个部分
     *      Header （头部）
     *      Payload （负载）
     *      Signature （签名）
     * 根据荷载生成JWT token
     * @param claims
     * @return
     */
    private String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)  //荷载
                .setExpiration(generateExpirationDate())  //过期时间
                .signWith(SignatureAlgorithm.HS512,secret)  //签名
                .compact();
    }

    /**
     * 生成token的失效时间
     * @return
     */
    private Date generateExpirationDate() {
        //系统当前时间+我们设置的存活时间
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从token中获取登陆用户名
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token){
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();  //通过荷载拿到用户名
        } catch (Exception e) {
            username = null;  //如果有异常，username直接返回null，拿不到
        }
        return username;
    }

    /**
     * 判断token是否有效
     *  1. 判断token是否过期
     *  2. 判断token中的用户名与userDetails中的用户名是否一致
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token,UserDetails userDetails){
       return (userDetails.getUsername()).equals(getUsernameFromToken(token))
               && !isTokenExpired(token);  //如果过期了就是true ，加！返回false表示token失效了
    }

    /**
     * 判断token是否可以被刷新
     * 注意：token有效才可以被刷新
     * @param token
     * @return
     */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refreshToken(String token){
        //给荷载当前时间
        Claims claims = getClaimsFromToken(token);
        Object put = claims.put(CLAIM_KEY_CREATED, new Date());
        //generateToken(claims) 刷新token时会更新失效时间
        return generateToken(claims);
    }
    /**
     * 判断token是否过期
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        //拿到一个时间，这个时间就是token的过期时间，
        // 我们只需要判断这个时间是不是在当前时间之前
        // ，如果是，说明token过期了，反之亦然
        Date expireDate = getExiredDateFromToken(token);
        return expireDate.before(new Date());
    }

    /**
     * 从token中拿到失效时间
     * @param token
     * @return
     */
    private Date getExiredDateFromToken(String token) {
        //还是从荷载中拿
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 从token中获取荷载
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            //从token中拿荷载需要我们生成token时给的签名，签名需要一直在才能拿到
            claims = Jwts.parser().
                    setSigningKey(secret) //
                    .parseClaimsJws(token)
                    .getBody();//才能拿body，就是荷载
        } catch (Exception e) {
           e.printStackTrace();
        }
         return claims;
    }
}
