package com.rocky.blogapi.common;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code; // 200:成功, 500:失敗
    private String message;
    private T data;

    // 快速建立成功回應
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.message = "操作成功";
        result.data = data;
        return result;
    }

    // 快速建立失敗回應
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.code = 500;
        result.message = message;
        return result;
    }
}