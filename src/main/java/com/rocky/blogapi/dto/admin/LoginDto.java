package com.rocky.blogapi.dto.admin;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}