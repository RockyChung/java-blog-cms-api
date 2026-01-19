package com.rocky.blogapi.controller;

import com.rocky.blogapi.dto.ArticleDto;
import com.rocky.blogapi.entity.Article;
import com.rocky.blogapi.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController // 告訴 Spring 這是寫 API 的，回傳 JSON
@RequestMapping("/api/articles") // 統一網址前綴
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    // 新增文章 API
    // URL: POST /api/articles
    @PostMapping
    public Article create(@RequestBody ArticleDto dto) {
        return articleService.createArticle(dto);
    }
}