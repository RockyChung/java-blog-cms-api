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

    // 使用 TEXT 類型儲存長文本
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // 瀏覽量 (預設 0)
    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private Long views = 0L;

    // 狀態 (0:草稿, 1:已發布)
    @Column(columnDefinition = "INTEGER DEFAULT 0")
    private Integer status = 0;

    // --- 關聯設定 ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "article_tag",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
}