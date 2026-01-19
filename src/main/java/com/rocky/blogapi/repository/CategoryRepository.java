package com.rocky.blogapi.repository;

import com.rocky.blogapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // 這裡什麼都不用寫！
    // JpaRepository 已經幫你寫好了 findAll, save, delete, findById...
}