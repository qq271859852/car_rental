package com.example.demo.service;

import com.example.demo.bean.entity.Order;

import java.util.Date;
import java.util.List;

/**
 * @author zhouxiong
 */
public interface OrderService {
    /**
     * find out orders whose rental date range are intersected between leftBoundary and rightBoundary
     * @param carIds
     * @param leftBoundary
     * @param rightBoundary
     * @return
     */
    List<Order> findDateIntersectionOrders(List<Long> carIds, Date leftBoundary, Date rightBoundary);

    void createOrder(Order order);

}
