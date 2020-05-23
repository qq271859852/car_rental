package com.example.demo.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * http interface result wrapper
 *
 * @author zhouxiong
 */
@Data
@NoArgsConstructor
public class ApiResult<T> {

    private static final ApiResult SUCCESS = new ApiResult();

    private boolean success = true;

    private String errMsg;

    private String errCode;

    private T data;

    public ApiResult(T t) {
        this.data = t;
    }

    public ApiResult(String errCode, String errMsg, T t) {
        this.errMsg = errMsg;
        this.errCode = errCode;
        this.data = t;
        this.success = false;
    }

    public static ApiResult errorResponse(String errCode, String errMsg) {
        return new ApiResult(errCode, errMsg, null);
    }

    public static <T> ApiResult<T> success(T obj) {
        return new ApiResult(obj);
    }

}
