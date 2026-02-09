package com.rocky.blogapi.controller.web;

import com.rocky.blogapi.common.Result;
import com.rocky.blogapi.dto.app.ArticleDto;
import com.rocky.blogapi.entity.Article;
import com.rocky.blogapi.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Tag(name="文件管理",description="提供文章的新增、查詢與列表功能")
@RestController
@RequestMapping("/api/web/articles") // 統一網址前綴
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    // 取得單篇文章 API
    // URL範例: GET /api/articles/1
    @Operation(summary = "取得文章詳情",description = "根據 ID 取得單篇文章內容")
    @GetMapping("/{id}")
    public Result<Article> getDetail(@PathVariable Long id) {

        Article article = articleService.getArticleById(id);
        return Result.success(article);
    }

    @Operation(summary = "取得文章列表",description = "支援分頁與排序，預設依建立時間倒序排列")
    @GetMapping
    public Result<Page<Article>> list(
            // @PageableDefault: 預設顯示第 0 頁，每頁 10 筆，按建立時間新->舊排序
            @PageableDefault(size = 10, sort = "createTime",direction = Sort.Direction.DESC) Pageable pageable) {

        return Result.success(articleService.getArticles(pageable));
    }
}