package com.example.commonutils.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    public static final int SUCCESS = 1;
    public static final int FAILED = 2;

    private final int code;
    private final String message;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }


}
