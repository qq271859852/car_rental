package com.example.demo.mapper;

import com.example.demo.bean.entity.Car;

import java.util.List;

public interface CarMapper {

    Car selectByPrimaryKey(Long id);

    List<String> selectAllCarModel();

    List<Car> search(String model);

}