package com.rocky.blogapi.config;

import com.rocky.blogapi.entity.Role;
import com.rocky.blogapi.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(RoleRepository roleRepository) {
        return args -> {
            // 檢查是否已經有資料，避免每次啟動都重複新增
            if (roleRepository.count() == 0) {

                Role admin = new Role();
                admin.setName("ROLE_ADMIN");
                admin.setDescription("系統管理者");

                Role user = new Role();
                user.setName("ROLE_USER");
                user.setDescription("系統使用者");

                // 批次儲存
                roleRepository.saveAll(List.of(admin, user));

                System.out.println("✅ 初始角色資料已匯入 Neon 資料庫！");
            } else {
                System.out.println("ℹ️ 角色資料已存在，跳過初始化。");
            }
        };
    }
}