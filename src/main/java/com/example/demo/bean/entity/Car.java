package com.example.demo.bean.entity;

import lombok.Data;

@Data
public class Car {
    private Long id;

    private String licensePlateNumber;

    private String model;

    private Long buyingPrice;

    private Long reservePrice;

}