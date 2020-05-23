package com.example.demo.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * abstract business exception
 * @author zhouxiong
 */
@NoArgsConstructor
@Getter
public abstract class BizException extends RuntimeException {
    protected String errCode;

    public BizException(String errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
    }
}
