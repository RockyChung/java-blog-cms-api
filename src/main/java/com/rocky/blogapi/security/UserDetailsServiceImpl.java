package com.rocky.blogapi.security;

import com.rocky.blogapi.entity.User;
import com.rocky.blogapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. 去資料庫找人
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("找不到使用者: " + username));

        // 2. 把我們的 Role 轉成 Spring Security 看得懂的 Authority
        // 格式通常是 "ROLE_ADMIN", "ROLE_USER"
        var authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), // 這裡應該要是加密後的密碼
                user.getEnabled(), // 是否啟用
                true,true,true, // 帳號過期、憑證過期、鎖定等檢查 (暫時都設 true)
                authorities
        );
    }

}
