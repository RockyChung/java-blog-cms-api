package com.rocky.blogapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Nationalized;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "category")
public class Category extends BaseEntity {

    // 增加@Nationalized 支援中文
    @Nationalized
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    // 增加@Nationalized 支援中文
    @Nationalized
    @Column(length = 200)
    private String description;
}