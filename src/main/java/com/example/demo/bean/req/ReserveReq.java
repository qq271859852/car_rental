package com.example.demo.bean.req;

import lombok.Data;
import org.joda.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

/**
 * @author zhouxiong
 */
@Data
public class ReserveReq {
    @NotNull
    private Long carId;

    @NotNull
    @FutureOrPresent
    private LocalDate pickupDate;

    @NotNull
    @FutureOrPresent
    private LocalDate dropOffDate;
}
