package com.example.demo.interceptor;

import com.example.demo.bean.ApiResult;
import com.example.demo.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult handleException(Exception e) {
        log.error("Unexpected exception occur", e);
        return ApiResult.errorResponse("A0000", "Server Error");
    }

    @ExceptionHandler
    public ApiResult handleBizException(BizException e) {
        log.warn("BizException occur", e);
        return ApiResult.errorResponse(e.getErrCode(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("Bean validation failure", e);
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();

        String tips = allErrors.stream()
            .map(objectError -> {
                String errorParam;
                if (objectError instanceof FieldError) {
                    errorParam = ((FieldError)objectError).getField();
                } else {
                    errorParam = objectError.getObjectName();
                }
                String errorMsg = objectError.getDefaultMessage();
                return String.format("field: '%s' %s", errorParam, errorMsg);
            })
            .collect(Collectors.joining(", "));

        return ApiResult.errorResponse("A0001", tips);
    }

}
