package com.rocky.blogapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "article")
public class Article extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 200)
    private String summary;

    // 【關鍵】PostgreSQL 的 Text 類型，可以存很長的文章
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // 瀏覽量 (預設 0)
    private Long views = 0L;

    // 狀態 (0:草稿, 1:已發布) - 面試時說用 Enum 會更好，但先用 Integer 比較快
    private Integer status = 0;

    // --- 關聯設定 ---

    // 1. 作者 (關聯到 User 表)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    // 2. 分類 (關聯到 Category 表)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // 3. 標籤 (多對多，會自動產生 article_tag 中間表)
    @ManyToMany
    @JoinTable(
            name = "article_tag",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
}