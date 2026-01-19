package com.rocky.blogapi.repository;

import com.rocky.blogapi.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    // 多繼承了 JpaSpecificationExecutor，以後做「多條件搜尋」會非常強大
}