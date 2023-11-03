package com.emt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    //设置密钥
    public static String signKey = "EMT";
    //设置过期时间
    public static Long expire = 43200000L;

    //生成JWT令牌
    public static String GenerateJwt(String username){
        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setSubject(username)//设置加密内容
                .signWith(SignatureAlgorithm.HS512, signKey)//设置加密算法
                .setExpiration(new Date(System.currentTimeMillis() + expire))//设置失效时间
                .compact();
    }
    public static Claims parseJwt(String jwt){
          return Jwts.parser()
                .setSigningKey(signKey)// 设置密钥
                .parseClaimsJws(jwt)// 设置令牌
                .getBody();// 获取内容
    }
}
