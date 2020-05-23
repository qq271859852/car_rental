package com.example.demo.exception;

/**
 * @author zhouxiong
 */
public class LoginFailedException extends BizException {

    public LoginFailedException() {
        super("A0002", "telephone or password error");
    }
}
