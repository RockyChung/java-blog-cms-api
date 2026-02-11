package com.rocky.blogapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_role")
public class Role extends BaseEntity {

    // 角色名稱，例如：ROLE_ADMIN, ROLE_USER
    // Spring Security 預設喜歡 "ROLE_" 開頭
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    // 中文描述，例如：系統管理員
    // PostgreSQL 原生支援 UTF-8，可直接儲存中文
    @Column(length = 50)
    private String description;
}