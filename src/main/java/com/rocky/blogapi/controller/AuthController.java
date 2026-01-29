package com.rocky.blogapi.controller;

import com.rocky.blogapi.common.Result;
import com.rocky.blogapi.dto.LoginDto;
import com.rocky.blogapi.dto.RegisterDto;
import com.rocky.blogapi.entity.Role;
import com.rocky.blogapi.entity.User;
import com.rocky.blogapi.repository.RoleRepository;
import com.rocky.blogapi.repository.UserRepository;
import com.rocky.blogapi.security.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "認證管理", description = "提供登入與註冊功能")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    // 新增這三個依賴
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "使用者登入")
    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );

        String token = jwtUtils.generateToken(loginDto.getUsername());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("username", loginDto.getUsername());

        return Result.success(response);
    }

    // 👇 新增這個註冊方法
    @Operation(summary = "使用者註冊", description = "註冊新帳號，預設會給予 ROLE_USER 權限")
    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterDto registerDto) {

        // 1. 檢查帳號是否已存在
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return Result.error("錯誤：使用者名稱已被使用！");
        }

        // 2. 檢查 Email 是否已存在 (如果您的 Entity 有設定 Email 唯一)
        // if (userRepository.existsByEmail(registerDto.getEmail())) { ... }

        // 3. 建立 User 物件
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setNickName(registerDto.getNickname());

        // 4. 【關鍵】密碼一定要加密！
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        user.setEnabled(true); // 預設啟用

        // 5. 設定預設權限 (ROLE_USER)
        // 注意：資料庫的 sys_role 表裡面必須要先有 'ROLE_USER' 這筆資料
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("錯誤：系統內部找不到 ROLE_USER 角色設定，請聯絡管理員。"));

        user.setRoles(Collections.singleton(userRole));

        // 6. 存入資料庫
        userRepository.save(user);

        return Result.success("註冊成功！請嘗試登入");
    }
}