package com.example.demo.bean.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Order {
    private Long id;

    private Long carId;

    private Long customerId;

    private Date pickUpDate;

    private Date dropOffDate;

    private Date createTime;

}