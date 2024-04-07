package com.example.commonutils.dto;

import lombok.Data;

@Data
public class IResponse<T> {
    public static final int ERR_AUTHORITY = 403;
    private int code;
    private String message;
    private T data;

    public static <T> IResponse<T> ok(T data) {
        IResponse<T> response = new IResponse<>();
        response.code = 200;
        response.message = "OK";
        response.data = data;
        return response;
    }

    public static <T> IResponse<T> error(int code, String message) {
        IResponse<T> response = new IResponse<>();
        response.code = code;
        response.message = message;
        return response;
    }
}
