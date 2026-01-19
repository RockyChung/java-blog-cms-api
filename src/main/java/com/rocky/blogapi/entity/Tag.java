package com.rocky.blogapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tag")
public class Tag extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String name;
}