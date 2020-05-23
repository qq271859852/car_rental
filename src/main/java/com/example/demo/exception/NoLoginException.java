package com.example.demo.exception;

/**
 * @author zhouxiong
 */
public class NoLoginException extends BizException {

    public NoLoginException() {
        super("A0003", "Customer is not logged in");
    }
}
