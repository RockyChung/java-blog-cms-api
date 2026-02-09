package com.rocky.blogapi.dto.app;

import lombok.Data;
import java.util.Set;

@Data
public class ArticleDto {
    // 前端只需要傳這些
    private String title;
    private String summary;
    private String content;

    // 只收 ID，不收整個物件
    private Long categoryId;
    private Set<Long> tagIds;
}