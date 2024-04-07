package com.example.commonutils.exception;

import com.example.commonutils.EnableGlobalExceptionHandler;
import com.example.commonutils.dto.IResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;

@ControllerAdvice
@ConditionalOnClass(EnableGlobalExceptionHandler.class)
@Log4j2
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public IResponse<String> handleBusinessException(BusinessException e, WebRequest webRequest) {
        HandlerMethod handlerMethod = (HandlerMethod) webRequest.getAttribute(
                HandlerMethod.class.getName(), WebRequest.SCOPE_REQUEST);
        if (handlerMethod != null) {
            log.info(handlerMethod.getMethod().getName() + "error code:" + e.getCode() + ", message:" + e.getMessage());
        }
        return IResponse.error(e.getCode(), e.getMessage());
    }
}