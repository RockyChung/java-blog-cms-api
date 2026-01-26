package com.rocky.blogapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            // 1. 從 Header 取得 Token
            String token = getTokenFromHeader(request);

            // 2. 如果 Token 存在且驗證通過
            if (token != null && jwtUtils.validateToken(token)) {

                // 3. 從 Token 解析出使用者名稱
                String username = jwtUtils.getUsernameFromToken(token);

                // 4. 載入使用者詳細資料 (包含權限)
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 5. 建立認證物件 (Authentication)
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. 【關鍵】把認證物件放入 SecurityContext，代表「這個人已登入」
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("無法設定使用者認證: {}", e.getMessage());
        }

        // 7. 繼續往下一個 Filter 走
        filterChain.doFilter(request, response);
    }

    // Helper: 解析 Header 裡的 "Bearer xxxxx"
    private String getTokenFromHeader(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // 去掉 "Bearer " 前綴
        }
        return null;
    }
}