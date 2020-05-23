package com.example.demo.service.impl;

import com.example.demo.bean.entity.Car;
import com.example.demo.bean.entity.Order;
import com.example.demo.bean.req.ReserveReq;
import com.example.demo.bean.resp.CarInfoResp;
import com.example.demo.exception.CarReservedException;
import com.example.demo.exception.IllegalParamException;
import com.example.demo.mapper.CarMapper;
import com.example.demo.service.CarService;
import com.example.demo.service.OrderService;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhouxiong
 */
@Service
public class CarServiceImpl implements CarService {

    @Resource
    private CarMapper carMapper;

    @Resource
    private OrderService orderService;

    @Override
    @Cacheable("carModel#30") // cache for 30s
    public List<String> carModels() {
        return carMapper.selectAllCarModel();
    }

    @Override
    public List<CarInfoResp> carsInfo(LocalDate start, LocalDate end, String model) {

        // search car basic info for rental
        List<Car> cars = carMapper.search(model);
        if (CollectionUtils.isEmpty(cars)) {
            return Collections.emptyList();
        }

        List<Long> carIds = cars
            .stream()
            .map(Car::getId)
            .collect(Collectors.toList());

        // find out orders whose rental date range are intersected between start and end
        Set<Long> reservedCarIds = orderService.findDateIntersectionOrders(carIds, start.toDate(), end.toDate())
            .stream()
            .map(Order::getCarId)
            .collect(Collectors.toSet());

        // build response
        return cars.stream()
            .map(car -> {
                CarInfoResp carInfoResp = new CarInfoResp();
                BeanUtils.copyProperties(car, carInfoResp);

                boolean canReserve = !reservedCarIds.contains(car.getId());
                carInfoResp.setCanReserve(canReserve);

                return carInfoResp;
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void carReservation(Long customerId, ReserveReq reserveReq) {
        Long carId = reserveReq.getCarId();
        LocalDate pickupDate = reserveReq.getPickupDate();
        LocalDate dropOffDate = reserveReq.getDropOffDate();

        Car car = carMapper.selectByPrimaryKey(carId);
        if (car == null) {
            throw new IllegalParamException("The company has no car with id: " + carId);
        }

        Set<Long> reservedCarIds = orderService.findDateIntersectionOrders(Lists.newArrayList(carId), pickupDate.toDate(), dropOffDate.toDate())
            .stream()
            .map(Order::getCarId)
            .collect(Collectors.toSet());

        if (CollectionUtils.isNotEmpty(reservedCarIds)) {
            throw new CarReservedException();
        }

        Order order = new Order();
        order.setCarId(carId);
        order.setCustomerId(customerId);
        order.setPickUpDate(pickupDate.toDate());
        order.setDropOffDate(dropOffDate.toDate());
        order.setCreateTime(new Date());
        orderService.createOrder(order);
    }
}
