package com.example.demo.exception;

/**
 * @author zhouxiong
 */
public class CarReservedException extends BizException {
    public CarReservedException() {
        super("A0004", "Can not reserve because the car has been reserved");
    }
}
