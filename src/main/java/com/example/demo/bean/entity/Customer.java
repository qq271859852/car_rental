package com.example.demo.bean.entity;

import lombok.Data;

@Data
public class Customer {
    private Long id;

    private String name;

    private Integer gender;

    private String telephone;

    private String password;
}