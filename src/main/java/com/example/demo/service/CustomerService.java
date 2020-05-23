package com.example.demo.service;

import com.example.demo.bean.entity.Customer;

/**
 * @author zhouxiong
 */
public interface CustomerService {

    Customer findCustomerInfo(String telephone);
}
