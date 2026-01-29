package com.rocky.blogapi.repository;

import com.rocky.blogapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    // 透過角色名稱找角色 (例如找 "ROLE_USER")
    Optional<Role> findByName(String name);
}