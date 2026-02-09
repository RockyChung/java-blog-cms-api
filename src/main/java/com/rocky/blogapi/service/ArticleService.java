package com.rocky.blogapi.service;

import com.rocky.blogapi.dto.app.ArticleDto;
import com.rocky.blogapi.entity.Article;
import com.rocky.blogapi.entity.Category;
import com.rocky.blogapi.entity.Tag;
import com.rocky.blogapi.repository.ArticleRepository;
import com.rocky.blogapi.repository.CategoryRepository;
import com.rocky.blogapi.repository.TagRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor // Lombok 神技：自動把 final 的變數生成建構子注入 (取代 @Autowired)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Transactional // 確保新增文章是「原子操作」，失敗會回滾
    public Article createArticle(ArticleDto dto) {

        // 1. 轉換 Article 物件
        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setSummary(dto.getSummary());
        article.setContent(dto.getContent());
        article.setStatus(1); // 預設直接發布

        // 2. 處理分類 (如果前端有傳 categoryId)
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("找不到該分類 ID: " + dto.getCategoryId()));
            article.setCategory(category);
        }

        // 3. 處理標籤 (如果前端有傳 tagIds)
        if (dto.getTagIds() != null && !dto.getTagIds().isEmpty()) {
            List<Tag> tags = tagRepository.findAllById(dto.getTagIds());
            article.setTags(new HashSet<>(tags));
        }

        // 4. 存入資料庫
        return articleRepository.save(article);
    }


    // 1. 查詢單篇文章 (給詳情頁用)
    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("文章不存在 ID: " + id));
    }

    // 2. 查詢文章列表 (給首頁用，支援分頁)
    // Page<Article> 比 List<Article> 更高級，它會包含 "總共幾頁"、"總共幾筆" 等資訊
    public Page<Article> getArticles(Pageable pageable){
        return articleRepository.findAll(pageable);
    }
}