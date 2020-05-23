package com.example.demo.exception;

/**
 * @author zhouxiong
 */
public class IllegalParamException extends BizException {
    public IllegalParamException(String errMsg) {
        super("A0005", errMsg);
    }
}
