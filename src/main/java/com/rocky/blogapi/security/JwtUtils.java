package com.rocky.blogapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component // 交給 Spring 管理
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // 取得簽名用的 Key
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // 1. 產生 Token (發證)
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // 把帳號放入 Token
                .setIssuedAt(new Date()) //發證時間
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 過期時間
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // 簽名
                .compact();
    }

    // 2. 從 Token 取得使用者名稱 (讀取)
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 3. 驗證 Token 是否有效
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // 如果過期、簽名不對、格式錯誤，都會噴 Exception
            log.error("JWT 驗證失敗: {}", e.getMessage());
        }
        return false;
    }
}