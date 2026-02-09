package com.rocky.blogapi.controller.admin;

import com.rocky.blogapi.common.Result;
import com.rocky.blogapi.dto.app.ArticleDto;
import com.rocky.blogapi.entity.Article;
import com.rocky.blogapi.service.ArticleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name="文件管理",description="提供文章的新增、查詢與列表功能")
@RestController
@RequestMapping("/admin/api/articles") // 統一網址前綴
@RequiredArgsConstructor
public class AdminArticleController {

    private final ArticleService articleService;

    // 新增文章 API
    // URL: POST /api/articles
    @PostMapping
    public Result<Article> create(@RequestBody ArticleDto dto) {
        Article article = articleService.createArticle(dto);
        return Result.success(article);
    }

}