package com.example.demo.service;

import com.example.demo.bean.req.ReserveReq;
import com.example.demo.bean.resp.CarInfoResp;

import org.joda.time.LocalDate;
import java.util.List;

/**
 * @author zhouxiong
 */
public interface CarService {

    List<String> carModels();

    List<CarInfoResp> carsInfo(LocalDate start, LocalDate end, String model);

    void carReservation(Long customerId, ReserveReq reserveReq);
}
