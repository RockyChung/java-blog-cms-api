package com.rocky.blogapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Nationalized;

@Data
@EqualsAndHashCode(callSuper = true) // 讓 Lombok 知道要包含父類的欄位
@Entity
@Table(name = "sys_user") // 指定 Table 名稱，加 sys_ 前綴比較專業
public class User extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 100)
    private String email;

    // 增加@Nationalized 支援中文
    @Nationalized
    @Column(name = "nick_name", length = 50)
    private String nickName;

    // 狀態 (true:啟用, false:停用)
    private Boolean enabled = true;

    // 【新增這段】
    // FetchType.EAGER: 為了方便，查詢使用者時順便把角色抓出來 (因為登入時一定會用到)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "sys_user_role", // 中間表的名稱 (自動生成)
            joinColumns = @JoinColumn(name = "user_id"), // 這一邊的 FK
            inverseJoinColumns = @JoinColumn(name = "role_id") // 另一邊的 FK
    )
    private java.util.Set<Role> roles;
}