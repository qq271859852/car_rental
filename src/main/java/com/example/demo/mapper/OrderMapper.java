package com.example.demo.mapper;

import com.example.demo.bean.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderMapper {

    int insert(Order record);

    List<Order> findDateIntersectionOrders(@Param("carIds") List<Long> carIds,
        @Param("leftBoundary") Date leftBoundary,
        @Param("rightBoundary") Date rightBoundary);
}