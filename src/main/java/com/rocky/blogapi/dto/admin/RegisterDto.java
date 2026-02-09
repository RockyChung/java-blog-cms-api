package com.rocky.blogapi.dto.admin;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String password;
    private String email;
    private String nickname;
}