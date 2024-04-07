package com.example.uaa.handler;

import com.example.commonutils.dto.IResponse;
import com.example.uaa.exception.AuthorityException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthorityException.class)
    @ResponseBody
    public IResponse<String> handleAuthorityException(AuthorityException e) {
        // 返回错误信息
        return IResponse.error(IResponse.ERR_AUTHORITY, e.getMessage());
    }
}
