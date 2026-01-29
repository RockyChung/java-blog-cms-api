package com.rocky.blogapi.repository;

import com.rocky.blogapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 我們需要根據 username 找到使用者 (登入用)
    // Spring Data JPA 會自動看懂這個方法名稱，幫你產生 SQL
    Optional<User> findByUsername(String username);

    // 2. 檢查帳號是否存在 (註冊檢查用) -> 您少的就是這一個！
    boolean existsByUsername(String username);

    // 3. (選用) 檢查 Email 是否存在
    boolean existsByEmail(String email);
}