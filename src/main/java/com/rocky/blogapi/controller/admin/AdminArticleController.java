package com.rocky.blogapi.controller.admin;

import com.rocky.blogapi.common.Result;
import com.rocky.blogapi.dto.app.ArticleDto;
import com.rocky.blogapi.entity.Article;
import com.rocky.blogapi.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Tag(name="後台文章管理",description="提供文章的新增、查詢與列表功能")
@RestController
@RequestMapping("/api/admin/articles") // 統一網址前綴
@RequiredArgsConstructor
@Slf4j
public class AdminArticleController {

    private final ArticleService articleService;

    @Operation(summary = "新增文章", description = "建立新的文章內容")
    @PostMapping
    public Result<Article> create(@RequestBody ArticleDto dto) {
        log.info(" ===== START Create Article ===== ");
        log.info(" ===== Title: {} ===== ", dto.getTitle());
        Article article = articleService.createArticle(dto);
        log.info(" ===== END Create Article (ID: {}) ===== ", article.getId());
        return Result.success(article);
    }

    @Operation(summary = "取得文章列表",description = "支援分頁與排序，預設依建立時間倒序排列")
    @GetMapping
    public Result<Page<Article>> list(
            // @PageableDefault: 預設顯示第 0 頁，每頁 10 筆，按建立時間新->舊排序
            @PageableDefault(size = 10, sort = "createTime",direction = Sort.Direction.DESC) Pageable pageable) {
        log.info(" ===== START Get Articles ===== ");
        Page<Article> articles = articleService.getArticles(pageable);
        log.info(" ===== END Get Articles (Total: {}) ===== ", articles.getTotalElements());
        return Result.success(articles);
    }
}