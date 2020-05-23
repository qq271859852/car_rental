package com.example.demo.mapper;

import com.example.demo.bean.entity.Customer;

public interface CustomerMapper {

    Customer selectByTelephone(String telephone);
}