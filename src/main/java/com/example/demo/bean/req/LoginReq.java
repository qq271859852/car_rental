package com.example.demo.bean.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhouxiong
 */
@Data
public class LoginReq {
    @NotBlank
    private String telephone;
    @NotBlank
    private String password;
}
