package com.rocky.blogapi.dto;

import lombok.Data;
import java.util.Set;

@Data
public class ArticleDto {
    // 前端只需要傳這些給我們
    private String title;
    private String summary;
    private String content;

    // 這裡我們只收 ID，不收整個物件 (關鍵！)
    private Long categoryId;
    private Set<Long> tagIds;
}