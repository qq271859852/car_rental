package com.example.demo.bean.resp;

import lombok.Data;

/**
 * @author zhouxiong
 */
@Data
public class CarInfoResp {
    private Long id;

    private String licensePlateNumber;

    private String model;

    private Long buyingPrice;

    private Long reservePrice;

    private Boolean canReserve;
}
