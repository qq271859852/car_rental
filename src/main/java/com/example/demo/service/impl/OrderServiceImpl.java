package com.example.demo.service.impl;

import com.example.demo.bean.entity.Order;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zhouxiong
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public List<Order> findDateIntersectionOrders(List<Long> carIds, Date leftBoundary, Date rightBoundary) {
        return orderMapper.findDateIntersectionOrders(carIds, leftBoundary, rightBoundary);
    }

    @Override
    public void createOrder(Order order) {
        orderMapper.insert(order);
    }
}
