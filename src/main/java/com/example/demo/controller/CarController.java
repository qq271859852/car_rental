package com.example.demo.controller;

import com.example.demo.bean.ApiResult;
import com.example.demo.bean.entity.Customer;
import com.example.demo.bean.req.ReserveReq;
import com.example.demo.bean.resp.CarInfoResp;
import com.example.demo.constant.CommonConstant;
import com.example.demo.exception.IllegalParamException;
import com.example.demo.service.CarService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zhouxiong
 */
@RestController
public class CarController {
    @Resource
    private CarService carService;

    /**
     * list all car model
     */
    @GetMapping("/car/model")
    public ApiResult<List<String>> carModel() {
        return ApiResult.success(carService.carModels());
    }

    /**
     * search all cars info
     *
     * @param start start date that wanted to reserve
     * @param end   end date that wanted to reserved
     * @param model the car's model
     */
    @GetMapping("/cars")
    public ApiResult<List<CarInfoResp>> carsInfo(
        @RequestParam LocalDate start,
        @RequestParam LocalDate end,
        String model) {

        if (start == null) {
            throw new IllegalParamException("the param start is missing");
        }
        if (end == null) {
            throw new IllegalParamException("the param end is missing");
        }

        model = StringUtils.trimToNull(model);

        return ApiResult.success(carService.carsInfo(start, end, model));
    }

    /**
     * reserve a car
     *
     * @param reserveReq
     */
    @PostMapping("/car/reservation")
    public ApiResult<Boolean> carReservation(HttpServletRequest request, @Validated @RequestBody ReserveReq reserveReq) {
        HttpSession session = request.getSession(false);
        Customer customer = (Customer)session.getAttribute(CommonConstant.CURRENT_CUSTOMER_KEY);
        Long customerId = customer.getId();
        carService.carReservation(customerId, reserveReq);
        return ApiResult.success(true);
    }
}
